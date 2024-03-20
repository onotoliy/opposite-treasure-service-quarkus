package com.github.onotoliy.opposite.treasure.dto.data.core;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

public record ExceptionDevice(
    @Parameter(name = "device", description = "device")
    String device,
    @Parameter(name = "agent", description = "agent")
    String agent,
    @Parameter(name = "message", description = "message")
    String message,
    @Parameter(name = "localizedMessage", description = "localizedMessage")
    String localizedMessage,
    @Parameter(name = "stackTrace", description = "stackTrace")
    String stackTrace
) {
}
