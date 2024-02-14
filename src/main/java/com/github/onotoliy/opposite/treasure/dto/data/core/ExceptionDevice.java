package com.github.onotoliy.opposite.treasure.dto.data.core;

public record ExceptionDevice(
        String device,
        String agent,
        String message,
        String localizedMessage,
        String stackTrace
) {
}
