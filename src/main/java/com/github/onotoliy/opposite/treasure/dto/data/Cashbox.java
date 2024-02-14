package com.github.onotoliy.opposite.treasure.dto.data;

import java.math.BigDecimal;
import java.time.Instant;

public record Cashbox(
        BigDecimal deposit,
        Instant lastUpdateDate
) {
}
