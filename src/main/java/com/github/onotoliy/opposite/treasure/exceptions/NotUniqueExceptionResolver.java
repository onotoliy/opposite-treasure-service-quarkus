package com.github.onotoliy.opposite.treasure.exceptions;

import com.github.onotoliy.opposite.treasure.dto.data.core.ExceptionInformation;
import com.github.onotoliy.opposite.treasure.dto.data.core.HTTPStatus;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Перехватчик ошибок.
 *
 * @author Anatoliy Pokhresnyi
 */
@Provider
public class NotUniqueExceptionResolver implements ExceptionMapper<NotUniqueException> {

    /**
     * Конструктор по умолчанию.
     */
    public NotUniqueExceptionResolver() {

    }

    @Override
    public Response toResponse(NotUniqueException exception) {
        return Response
                .status(Response.Status.CONFLICT)
                .entity(new ExceptionInformation(HTTPStatus.BAD_REQUEST, exception.getMessage()))
                .build();
    }
}
