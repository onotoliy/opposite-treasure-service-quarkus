package com.github.onotoliy.opposite.treasure.dto.data.page;

public record Paging(
        /**
         * С какой записи выводить.
         */
        int start,

        /**
         * Размер странцы.
         */
        int size
){
}
