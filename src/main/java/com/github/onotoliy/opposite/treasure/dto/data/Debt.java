package com.github.onotoliy.opposite.treasure.dto.data;

import com.github.onotoliy.opposite.treasure.dto.data.core.HasAuthor;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasCreationDate;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasName;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasUUID;
import com.github.onotoliy.opposite.treasure.dto.data.core.Option;

import java.time.Instant;
import java.util.UUID;

public record Debt(
        Event event,
        Deposit deposit
) implements HasUUID, HasName, HasCreationDate, HasAuthor {

    @Override
    public Option author() {
        return null;
    }

    @Override
    public Instant creationDate() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public UUID uuid() {
        return null;
    }
}
