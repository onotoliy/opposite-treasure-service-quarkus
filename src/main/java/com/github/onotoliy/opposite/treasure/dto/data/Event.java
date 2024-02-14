package com.github.onotoliy.opposite.treasure.dto.data;

import com.github.onotoliy.opposite.treasure.dto.data.core.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record Event(
        UUID uuid,
        String name,
        BigDecimal contribution,
        BigDecimal total,
        Instant deadline,
        Instant creationDate,
        Option author,
        Instant deletionDate
) implements HasUUID, HasName, HasCreationDate, HasAuthor, HasDeletionDate {
}
