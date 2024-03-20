package com.github.onotoliy.opposite.treasure.dto.data;

import com.github.onotoliy.opposite.treasure.dto.data.core.SearchParameter;
import jakarta.ws.rs.QueryParam;

import java.util.UUID;

/**
 * Поисковые параметры для депозита.
 *
 * @author Anatoliy Pokhresnyi
 */
public class DebtSearchParameter extends SearchParameter {

    /**
     * Конструктор.
     *
     * @param offset Количество записей которое необходимо пропустить.
     * @param numberOfRows Размер страницы.
     */
    public DebtSearchParameter(
            @QueryParam("person") final UUID person,
            @QueryParam("event") final UUID event,
            @QueryParam("offset") final int offset,
            @QueryParam("numberOfRows") final int numberOfRows
    ) {
        super(offset, numberOfRows);
    }
}
