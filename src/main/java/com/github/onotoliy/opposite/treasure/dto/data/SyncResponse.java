package com.github.onotoliy.opposite.treasure.dto.data;

import com.github.onotoliy.opposite.treasure.dto.data.core.HasName;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasUUID;

import java.util.UUID;

public record SyncResponse(
        UUID uuid,
        String name,
        int status,
        String exception
) implements HasUUID, HasName {
}
