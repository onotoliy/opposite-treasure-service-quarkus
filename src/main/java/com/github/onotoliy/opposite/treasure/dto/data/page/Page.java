package com.github.onotoliy.opposite.treasure.dto.data.page;

import java.util.List;

public record Page<T>(
        /**
         * Мета данные о списке.
         */
        Meta meta,

        /**
         * Данные.
         */
        List<T> context
) {
}
