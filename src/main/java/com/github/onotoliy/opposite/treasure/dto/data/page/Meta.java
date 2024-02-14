package com.github.onotoliy.opposite.treasure.dto.data.page;

public record Meta(
        /**
         * Общее количество записей.
         */
        int total,

        /**
         * Мета данные страницы.
         */
        Paging paging
) {
}
