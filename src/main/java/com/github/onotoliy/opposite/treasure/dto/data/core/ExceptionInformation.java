package com.github.onotoliy.opposite.treasure.dto.data.core;

public record ExceptionInformation(
        HTTPStatus status,
        String message
) {
}
