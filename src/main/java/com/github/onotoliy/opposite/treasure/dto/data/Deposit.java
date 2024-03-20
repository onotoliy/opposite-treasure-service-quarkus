package com.github.onotoliy.opposite.treasure.dto.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasAuthor;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasCreationDate;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasName;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasUUID;
import com.github.onotoliy.opposite.treasure.dto.data.core.Option;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Schema(description = "Депозит", requiredProperties = {"uuid", "name", "deposit"})
public record Deposit(
        @Schema(name = "uuid", description = "Уникальный идентификатор")
        UUID uuid,
        @Schema(name = "name", description = "Название")
        String name,
        @Schema(name = "deposit", description = "Сумма")
        BigDecimal deposit
) implements HasUUID, HasName, HasCreationDate, HasAuthor {

    @Override
    @JsonIgnore
    @Schema(hidden = true)
    public Option author() {
        return null;
    }

    @Override
    @JsonIgnore
    @Schema(hidden = true)
    public Instant creationDate() {
        return null;
    }
}
