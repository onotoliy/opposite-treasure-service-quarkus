package com.github.onotoliy.opposite.treasure.web.core;

import com.github.onotoliy.opposite.treasure.dto.data.core.SearchParameter;
import com.github.onotoliy.opposite.treasure.dto.data.SyncResponse;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasAuthor;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasCreationDate;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasName;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasUUID;

import java.util.UUID;

/**
 * Базовый WEB сервис управления данными.
 *
 * @param <E> Объект.
 * @param <P> Поисковые параметры объекта.
 * @author Anatoliy Pokhresnyi
 */
public abstract class AbstractModifierResource<
    E extends HasUUID & HasName & HasCreationDate & HasAuthor,
    P extends SearchParameter>
extends AbstractReaderResource<E, P>
implements ModifierResource<E, P> {

    @Override
    public E create(final E dto) {
        return null;
    }

    @Override
    public E update(final E dto) {
        return null;
    }

    @Override
    public void delete(final UUID uuid) {
    }

    @Override
    public SyncResponse sync(final E dto) {
        return null;
    }
}
