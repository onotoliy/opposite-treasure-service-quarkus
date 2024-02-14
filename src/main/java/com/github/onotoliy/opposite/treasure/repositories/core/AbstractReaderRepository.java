package com.github.onotoliy.opposite.treasure.repositories.core;

import com.github.onotoliy.opposite.treasure.dto.data.core.*;
import com.github.onotoliy.opposite.treasure.dto.SearchParameter;
import com.github.onotoliy.opposite.treasure.dto.data.page.Meta;
import com.github.onotoliy.opposite.treasure.dto.data.page.Page;
import com.github.onotoliy.opposite.treasure.dto.data.page.Paging;
import com.github.onotoliy.opposite.treasure.exceptions.NotFoundException;
import com.github.onotoliy.opposite.treasure.rpc.KeycloakRPC;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.OrderField;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;

import static com.github.onotoliy.opposite.treasure.jooq.Tables.TREASURE_VERSION;

/**
 * Базовый репозиторий чтения записей из БД.
 *
 * @param <E> Объект.
 * @param <P> Поисковые параметры.
 * @param <R> Запись из БД
 * @param <T> Таблица в БД
 * @author Anatoliy Pokhresnyi
 */
public abstract class AbstractReaderRepository<
    E extends HasUUID & HasName & HasCreationDate & HasAuthor,
    P extends SearchParameter,
    R extends Record,
    T extends Table<R>>
implements ReaderRepository<E, P> {

    /**
     * Таблица.
     */
    protected final T table;

    /**
     * Уникальный идентификатор.
     */
    protected final TableField<R, UUID> uuid;

    /**
     * Название.
     */
    protected final TableField<R, String> name;

    /**
     * Автор.
     */
    protected final TableField<R, UUID> author;

    /**
     * Дата создания.
     */
    protected final TableField<R, Instant> creationDate;

    /**
     * Дата удаления.
     */
    protected final TableField<R, Instant> deletionDate;

    /**
     * Контекст подключения к БД.
     */
    protected final DSLContext dsl;

    /**
     * Сервис чтения пользователей.
     */
    protected final KeycloakRPC user;

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
    protected AbstractReaderRepository(
            final T table,
            final TableField<R, UUID> uuid,
            final TableField<R, String> name,
            final TableField<R, UUID> author,
            final TableField<R, Instant> creationDate,
            final TableField<R, Instant> deletionDate,
            final DSLContext dsl,
            final KeycloakRPC user) {
        this.table = table;
        this.uuid = uuid;
        this.name = name;
        this.author = author;
        this.creationDate = creationDate;
        this.deletionDate = deletionDate;
        this.dsl = dsl;
        this.user = user;
    }

    /**
     * Преобзазование записи из БД в объект.
     *
     * @param record Запись из БД.
     * @return Объект.
     */
    protected abstract E toDTO(Record record);

    /**
     * Получение select запроса из таблицы.
     *
     * @return Запрос.
     */
    protected SelectJoinStep<Record> findQuery() {
        return dsl.select().from(table);
    }

    @Override
    public Optional<E> getOptional(final UUID uuid) {
        return findQuery().where(this.uuid.eq(uuid)).fetchOptional(this::toDTO);
    }

    @Override
    public E get(final UUID uuid) {
        return getOptional(uuid).orElseThrow(
            () -> new NotFoundException(table, uuid));
    }

    @Override
    public List<Option> getAll() {
        return findQuery().where(deletionDate.isNull())
                          .fetch(record -> new Option(
                                  record.getValue(uuid, UUID.class).toString(),
                                  record.getValue(name, String.class))
                          );
    }

    @Override
    public Option version() {
        return dsl.select()
           .from(TREASURE_VERSION)
           .where(TREASURE_VERSION.NAME.eq(table.getName()))
           .fetchOptional(record -> new Option(
                   record.getValue(TREASURE_VERSION.NAME, String.class),
                   record.getValue(TREASURE_VERSION.VERSION, BigDecimal.class).toString()
           ))
           .orElse(new Option(table.getName(), "0"));
    }

    @Override
    public Page<E> sync(final long version,
                        final int offset,
                        final int numberOfRows) {
       Condition condition = version == 0
           ? DSL.noCondition()
           : creationDate.greaterOrEqual(Instant.ofEpochMilli(version));

        return new Page<>(
            new Meta(
                dsl.selectCount()
                   .from(table)
                   .where(condition)
                   .fetchOptional(0, int.class)
                   .orElse(0),
                new Paging(offset, numberOfRows)),
            findQuery().where(condition)
                       .orderBy(orderBy())
                       .offset(offset)
                       .limit(numberOfRows)
                       .fetch(this::toDTO));
    }

    @Override
    public Page<E> getAll(final P parameter) {
        return new Page<>(
            new Meta(
                dsl.selectCount()
                   .from(table)
                   .where(where(parameter))
                   .fetchOptional(0, int.class)
                   .orElse(0),
                new Paging(parameter.offset(), parameter.numberOfRows())),
            findQuery().where(where(parameter))
                       .orderBy(orderBy())
                       .offset(parameter.offset())
                       .limit(parameter.numberOfRows())
                       .fetch(this::toDTO));
    }

    /**
     * Получение колонок по которым будет производиться сортировка.
     *
     * @return Колонки по которым будет производиться сортировка.
     */
    protected List<? extends OrderField<?>> orderBy() {
        return new LinkedList<>(Collections.singleton(creationDate.desc()));
    }

    /**
     * Получение условий выборки данных из БД.
     *
     * @param parameter Поисковы параметры.
     * @return Условия выборки данных из БД.
     */
    protected List<Condition> where(final P parameter) {
        return new LinkedList<>(Collections.singleton(deletionDate.isNull()));
    }

    /**
     * Преобразование пользователя из уникального идентификатора в объект.
     *
     * @param record Запись из БД.
     * @param field Колонка содержащая уникальный идентификатор пользователя.
     * @return Пользователь.
     */
    protected Option formatUser(final Record record, final Field<UUID> field) {
        return user.find(record.getValue(field, UUID.class));
    }
}
