package com.github.onotoliy.opposite.treasure.web.core;

import com.github.onotoliy.opposite.treasure.dto.data.core.SearchParameter;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasAuthor;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasCreationDate;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasName;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasUUID;
import com.github.onotoliy.opposite.treasure.dto.data.core.Option;
import com.github.onotoliy.opposite.treasure.dto.data.page.Page;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;
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
     * Получение объекта.
     *
     * @param uuid Уникальный идентификатор объекта.
     * @return Объект.
     */
    @GET
    @Path(value = "/{uuid}")
    @Operation(operationId = "get", description = "Получение объекта")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    E get(@PathParam("uuid") UUID uuid);

    @GET
    @Operation(operationId = "getAll")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Page<E> getAll(@BeanParam P parameter);


    /**
     * Получение версии сущности.
     *
     * @return Версия сущности.
     */
//    @GET
//    @Path(value = "/version")
//    @Operation(operationId = "getVersion", description = "Получение версии сущности")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    Option version();

    /**
     * Данные, которые необходимо синхронизировать.
     *
     * @param version Версия объекта.
     * @param offset Количество записей которое необходимо пропустить.
     * @param numberOfRows Размер страницы.
     * @return Данные, которые необходимо синхронизировать.
     */
//    @GET
//    @Path(value = "/sync")
//    @Operation(operationId = "readSync", description = "Данные, которые необходимо синхронизировать")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    Page<E> sync(
//            @QueryParam(value = "version")
//            long version,
//            @QueryParam(value = "offset") @DefaultValue("0")
//            final int offset,
//            @QueryParam(value = "numberOfRows") @DefaultValue("10")
//            final int numberOfRows
//    );



//    @GET
//    @Operation(operationId = "getAllOptions")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    List<Option> getAllOptions();
//

}
