package com.github.onotoliy.opposite.treasure.web.core;

import com.github.onotoliy.opposite.treasure.dto.data.core.SearchParameter;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasAuthor;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasCreationDate;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasName;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasUUID;
import com.github.onotoliy.opposite.treasure.dto.data.core.Option;
import com.github.onotoliy.opposite.treasure.dto.data.page.Page;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

import java.util.UUID;

/**
 * Интерфейс базового WEB сервиса чтения данных.
 *
 * @param <E> Объект.
 * @author Anatoliy Pokhresnyi
 */
public interface ReaderResource<
    E extends HasUUID & HasName & HasCreationDate & HasAuthor,
    P extends SearchParameter> {


    /**
     * Получение версии сущности.
     *
     * @return Версия сущности.
     */
    @GET
    @Path(value = "/version")
    Option version();

    /**
     * Данные, которые необходимо синхронизировать.
     *
     * @param version Версия объекта.
     * @param offset Количество записей которое необходимо пропустить.
     * @param numberOfRows Размер страницы.
     * @return Данные, которые необходимо синхронизировать.
     */
    @GET
    @Path(value = "/sync")
    Page<E> sync(
            @QueryParam(value = "version")
            long version,
            @QueryParam(value = "offset") @DefaultValue("0")
            final int offset,
            @QueryParam(value = "numberOfRows") @DefaultValue("10")
            final int numberOfRows
    );

    /**
     * Получение объекта.
     *
     * @param uuid Уникальный идентификатор объекта.
     * @return Объект.
     */
    @GET
    @Path(value = "/{uuid}")
    E get(@PathParam("uuid") UUID uuid);

}
