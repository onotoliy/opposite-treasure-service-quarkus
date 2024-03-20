package com.github.onotoliy.opposite.treasure.dto.data;


import com.github.onotoliy.opposite.treasure.dto.data.core.SearchParameter;
import jakarta.ws.rs.QueryParam;

/**
 * Поисковые параметры для события.
 *
 * @author Anatoliy Pokhresnyi
 */
public class EventSearchParameter extends SearchParameter {

    /**
     * Название.
     */
    private final String name;

    /**
     * Конструктор.
     *
     * @param name Название.
     * @param offset Количество записей которое необходимо пропустить.
     * @param numberOfRows Размер страницы.
     */
    public EventSearchParameter(@QueryParam("name") final String name,
                                @QueryParam("offset") final int offset,
                                @QueryParam("numberOfRows") final int numberOfRows) {
        super(offset, numberOfRows);

        this.name = name;
    }

    /**
     * Проверяет необходимо ли производить поиск по названию.

     * @return Результат проверки.
     */
    public boolean hasName() {
        return false;
    }

    /**
     * Получает название.
     *
     * @return Название.
     */
    public String getName() {
        return name;
    }
}
