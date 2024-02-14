package com.github.onotoliy.opposite.treasure.repositories.core;

import com.github.onotoliy.opposite.treasure.dto.SearchParameter;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasAuthor;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasCreationDate;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasName;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasUUID;
import com.github.onotoliy.opposite.treasure.exceptions.NotFoundException;
import com.github.onotoliy.opposite.treasure.exceptions.NotUniqueException;
import com.github.onotoliy.opposite.treasure.rpc.KeycloakRPC;
import com.github.onotoliy.opposite.treasure.utils.GUIDs;
import com.github.onotoliy.opposite.treasure.utils.Strings;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UpdateConditionStep;
import org.jooq.UpdateSetMoreStep;
import org.jooq.impl.DSL;

import static com.github.onotoliy.opposite.treasure.jooq.Tables.TREASURE_VERSION;

/**
 * Базовый репозиторий управления записями из БД.
 *
 * @param <E> Объект.
 * @param <P> Поисковые параметры.
 * @param <R> Запись из БД
 * @param <T> Таблица в БД
 * @author Anatoliy Pokhresnyi
 */
public abstract class AbstractModifierRepository<
    E extends HasUUID & HasName & HasCreationDate & HasAuthor,
    P extends SearchParameter,
    R extends Record,
    T extends Table<R>>
extends AbstractReaderRepository<E, P, R, T>
implements ModifierRepository<E, P> {

    /**
     * Конструктор.
     *
     * @param table Таблица.
     * @param uuid Уникальный идентификатор.
     * @param name Название.
     * @param author Автор.
     * @param creationDate Дата создания.
     * @param deletionDate Дата удаления.
     * @param dsl Контекст подключения к БД.
     * @param user Сервис чтения пользователей.
     */
    protected AbstractModifierRepository(
            final T table,
            final TableField<R, UUID> uuid,
            final TableField<R, String> name,
            final TableField<R, UUID> author,
            final  TableField<R, Instant> creationDate,
            final TableField<R, Instant> deletionDate,
            final DSLContext dsl,
            final KeycloakRPC user) {
        super(table, uuid, name, author, creationDate, deletionDate, dsl, user);
    }

    @Override
    public void transaction(final Consumer<Configuration> consumer) {
        dsl.transaction(consumer::accept);
    }

    @Override
    public E create(final E dto) {
        return execute(dto, insertQuery(dto));
    }

    @Override
    public E create(final Configuration configuration, final E dto) {
        return execute(dto, insertQuery(configuration, dto));
    }

    @Override
    public E update(final E dto) {
        return execute(dto, updateQuery(dto).where(uuid.eq(GUIDs.parse(dto))));
    }

    @Override
    public E update(final Configuration configuration, final E dto) {
        return execute(dto, updateQuery(configuration, dto).where(
            uuid.eq(GUIDs.parse(dto))));
    }

    @Override
    public void delete(final UUID uuid) {
        execute(uuid, deleteQuery(uuid));
    }

    @Override
    public void delete(final Configuration configuration, final UUID uuid) {
        execute(uuid, deleteQuery(configuration, uuid));
    }

    /**
     * Получение delete from запроса из таблицы.
     *
     * @param configuration Настройка транзакции.
     * @param uuid Уникальный идентификатор.
     * @return Запрос.
     */
    protected UpdateConditionStep<R> deleteQuery(
            final Configuration configuration,
            final UUID uuid) {
        return DSL.using(configuration)
                  .update(table)
                  .set(deletionDate, Instant.now())
                  .where(this.uuid.eq(uuid));
    }

    /**
     * Получение insert into запроса из таблицы.
     *
     * @param configuration Настройка транзакции.
     * @param dto Объект.
     * @return Запрос.
     */
    protected InsertSetMoreStep<R> insertQuery(
        final Configuration configuration,
        final E dto
    ) {
        setVersion(configuration, dto);

        return DSL.using(configuration)
                  .insertInto(table)
                  .set(uuid, GUIDs.parse(dto))
                  .set(name, dto.name())
                  .set(creationDate, dto.creationDate())
                  .set(author, GUIDs.parse(dto.author()));
    }

    /**
     * Получение update запроса из таблицы.
     *
     * @param configuration Настройка транзакции.
     * @param dto Объект.
     * @return Запрос.
     */
    protected UpdateSetMoreStep<R> updateQuery(
        final Configuration configuration,
        final E dto
    ) {
        setVersion(configuration, dto);

        return DSL.using(configuration)
                  .update(table)
                  .set(name, dto.name())
                  .set(creationDate, dto.creationDate())
                  .set(author, GUIDs.parse(dto.author()));
    }

    /**
     * Получение delete from запроса из таблицы.
     *
     * @param uuid Уникальный идентификатор.
     * @return Запрос.
     */
    protected UpdateConditionStep<R> deleteQuery(final UUID uuid) {
        return dsl.transactionResult(
            configuration -> deleteQuery(configuration, uuid));
    }

    /**
     * Получение insert into запроса из таблицы.
     *
     * @param dto Объект.
     * @return Запрос.
     */
    protected InsertSetMoreStep<R> insertQuery(final E dto) {
        return dsl.transactionResult(
            configuration -> insertQuery(configuration, dto));
    }

    /**
     * Получение update запроса из таблицы.
     *
     * @param dto Объект.
     * @return Запрос.
     */
    protected UpdateSetMoreStep<R> updateQuery(final E dto) {
        return dsl.transactionResult(
            configuration -> updateQuery(configuration, dto));
    }

    /**
     * Исполнение запроса на обновление данных.
     *
     * @param dto Объект.
     * @param query Запрос.
     * @return Объект.
     */
    private E execute(final E dto, final Query query) {
        execute(GUIDs.parse(dto), query);

        return dto;
    }

    /**
     * Исполнение запроса на обновление данных.
     *
     * @param uuid Уникальный идентификатор объекта.
     * @param query Запрос.
     */
    private void execute(final UUID uuid, final Query query) {
        int count = query.execute();

        if (count == 0) {
            throw new NotFoundException(table, uuid);
        }

        if (count > 1) {
            throw new NotUniqueException(table, uuid);
        }
    }

    /**
     * Изменение версии справочника.
     *
     * @param configuration Настройка транзакции.
     * @param dto Объект.
     */
    protected void setVersion(
        final Configuration configuration,
        final E dto
    ) {
        Instant timestamp = dto.creationDate() == null ? Instant.now() : dto.creationDate();

        BigDecimal version = BigDecimal.valueOf(
            Objects.requireNonNull(timestamp).toEpochMilli());

        DSL.using(configuration)
           .update(TREASURE_VERSION)
           .set(TREASURE_VERSION.VERSION, version)
           .where(TREASURE_VERSION.NAME.eq(table.getName()))
           .execute();
    }
}
