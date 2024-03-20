package com.github.onotoliy.opposite.treasure.web.core;


import com.github.onotoliy.opposite.treasure.dto.data.core.SearchParameter;
import com.github.onotoliy.opposite.treasure.dto.data.SyncResponse;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasAuthor;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasCreationDate;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasName;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasUUID;
import jakarta.ws.rs.*;

import java.util.UUID;

/**
 * Интерфейс базового WEB сервиса управления данными.
 *
 * @param <E> Объект.
 * @author Anatoliy Pokhresnyi
 */
public interface ModifierResource<
    E extends HasUUID & HasName & HasCreationDate & HasAuthor,
    P extends SearchParameter>
extends ReaderResource<E, P> {

    /**
     * Создание объекта.
     *
     * @param dto Объект.
     * @return Созданный объект.
     */
    @POST
    E create(E dto);

    /**
     * Изменение объекта.
     *
     * @param dto Объект.
     * @return Измененный объект.
     */
    @PUT
    E update(E dto);

    /**
     * Удаление объекта.
     *
     * @param uuid Уникальный идентификатор объекта.
     */
    @DELETE
    @Path(value = "/{uuid}")
    void delete(@PathParam("uuid") UUID uuid);

    /**
     * Синхронизация объекта.
     *
     * @param dto Объект.
     * @return Измененный объект.
     */
    @PUT
    @Path(value = "/sync")
    SyncResponse sync(E dto);

}
