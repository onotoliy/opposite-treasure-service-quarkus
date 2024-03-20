package com.github.onotoliy.opposite.treasure.dto.data;

import com.github.onotoliy.opposite.treasure.dto.data.core.SearchParameter;
import jakarta.ws.rs.QueryParam;

/**
 * Поисковые параметры для депозита.
 *
 * @author Anatoliy Pokhresnyi
 */
public class DepositSearchParameter extends SearchParameter {

    /**
     * Конструктор.
     *
     * @param offset Количество записей которое необходимо пропустить.
     * @param numberOfRows Размер страницы.
     */
    public DepositSearchParameter(@QueryParam("offset") final int offset, @QueryParam("numberOfRows") final int numberOfRows) {
        super(offset, numberOfRows);
    }
}
