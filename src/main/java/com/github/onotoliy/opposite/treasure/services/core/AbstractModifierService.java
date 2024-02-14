package com.github.onotoliy.opposite.treasure.services.core;

import com.github.onotoliy.opposite.treasure.dto.SearchParameter;
import com.github.onotoliy.opposite.treasure.dto.data.SyncResponse;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasAuthor;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasCreationDate;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasName;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasUUID;
import com.github.onotoliy.opposite.treasure.exceptions.ModificationException;
import com.github.onotoliy.opposite.treasure.exceptions.NotFoundException;
import com.github.onotoliy.opposite.treasure.exceptions.NotUniqueException;
import com.github.onotoliy.opposite.treasure.repositories.core.ModifierRepository;
import com.github.onotoliy.opposite.treasure.utils.GUIDs;

import java.util.UUID;

import org.apache.http.HttpStatus;
import org.jooq.Configuration;

/**
 * Базовый сервис управления объектами.
 *
 * @param <E> Объект.
 * @param <P> Поисковые параметры.
 * @param <R> Запись из БД
 * @author Anatoliy Pokhresnyi
 */
public abstract class AbstractModifierService<
    E extends HasUUID & HasName & HasCreationDate & HasAuthor,
    P extends SearchParameter,
    R extends ModifierRepository<E, P>>
extends AbstractReaderService<E, P, R>
implements ModifierService<E, P> {

    /**
     * Конструктор.
     *
     * @param repository Репозиторий.
     */
    public AbstractModifierService(final R repository) {
        super(repository);
    }

    @Override
    public E create(final E dto) {
        repository.transaction(configuration -> create(configuration, dto));

        return get(GUIDs.parse(dto));
    }

    /**
     * Создание объекта.
     *
     * @param configuration Настройки транзакции.
     * @param dto Объект.
     */
    protected void create(final Configuration configuration, final E dto) {
        repository.create(configuration, dto);
    }

    @Override
    public E update(final E dto) {
        repository.transaction(configuration -> update(configuration, dto));

        return get(GUIDs.parse(dto));
    }

    /**
     * Изменение объекта.
     *
     * @param configuration Настройки транзакции.
     * @param dto Объект.
     */
    protected void update(final Configuration configuration, final E dto) {
        repository.update(configuration, dto);
    }

    @Override
    public void delete(final UUID uuid) {
        repository.transaction(configuration -> delete(configuration, uuid));
    }

    /**
     * Удаление объекта.
     *
     * @param configuration Настройки транзакции.
     * @param uuid Уникальный идентификатор.
     */
    protected void delete(final Configuration configuration, final UUID uuid) {
        repository.delete(configuration, uuid);
    }

    @Override
    public SyncResponse sync(final E dto) {
        try {
            repository.transaction(configuration -> repository
                .getOptional(GUIDs.parse(dto))
                .ifPresentOrElse(record -> update(configuration, dto),
                                 () -> create(configuration, dto)));

            return new SyncResponse(
                dto.uuid(), dto.name(), HttpStatus.SC_OK, null
            );
        } catch (ModificationException exception) {
            return new SyncResponse(
                dto.uuid(),
                dto.name(),
                HttpStatus.SC_BAD_REQUEST,
                exception.getMessage()
            );
        } catch (NotFoundException exception) {
            return new SyncResponse(
                dto.uuid(),
                dto.name(),
                HttpStatus.SC_NOT_FOUND,
                exception.getMessage()
            );
        } catch (NotUniqueException exception) {
            return new SyncResponse(
                dto.uuid(),
                dto.name(),
                HttpStatus.SC_CONFLICT,
                exception.getMessage()
            );
        }
    }
}
