package com.github.onotoliy.opposite.treasure.dto.data;

import com.github.onotoliy.opposite.treasure.dto.data.core.HasName;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasUUID;

import java.math.BigDecimal;
import java.util.UUID;

public record Deposit(
        UUID uuid,
        String name,
        BigDecimal deposit
) implements HasUUID, HasName {
}
