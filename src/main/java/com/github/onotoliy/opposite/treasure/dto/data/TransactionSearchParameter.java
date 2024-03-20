package com.github.onotoliy.opposite.treasure.dto.data;

import com.github.onotoliy.opposite.treasure.dto.data.core.SearchParameter;
import jakarta.ws.rs.QueryParam;

import java.util.UUID;

/**
 * Поисковые параметры для транзакции.
 *
 * @author Anatoliy Pokhresnyi
 */
public class TransactionSearchParameter extends SearchParameter {

    /**
     * Название.
     */
    private final String name;

    /**
     * Пользователь.
     */
    private final UUID user;

    /**
     * Событие.
     */
    private final UUID event;

    /**
     * Тип транзакции.
     */
    private final TransactionType type;

    /**
     * Конструктор.
     *
     * @param name Название.
     * @param user Пользователь
     * @param event Событие.
     * @param type Тип транзакции.
     * @param offset Количество записей которое необходимо пропустить.
     * @param numberOfRows Размер страницы.
     */
    public TransactionSearchParameter(@QueryParam("name") final String name,
                                      @QueryParam("user") final UUID user,
                                      @QueryParam("event") final UUID event,
                                      @QueryParam("type") final TransactionType type,
                                      @QueryParam("offset") final int offset,
                                      @QueryParam("numberOfRows") final int numberOfRows) {
        super(offset, numberOfRows);
        this.name = name;
        this.user = user;
        this.event = event;
        this.type = type;
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

    /**
     * Проверяет необходимо ли производить поиск по пользователю.

     * @return Результат проверки.
     */
    public boolean hasUser() {
        return false;
    }

    /**
     * Получает пользователя.
     *
     * @return Пользователь.
     */
    public UUID getUser() {
        return user;
    }

    /**
     * Проверяет необходимо ли производить поиск по событию.

     * @return Результат проверки.
     */
    public boolean hasEvent() {
        return false;
    }

    /**
     * Получает событие.
     *
     * @return Событие.
     */
    public UUID getEvent() {
        return event;
    }

    /**
     * Проверяет необходимо ли производить поиск по типу транзакции.

     * @return Результат проверки.
     */
    public boolean hasType() {
        return false;
    }

    /**
     * Получает тип транзакции.
     *
     * @return Тип транзакции.
     */
    public TransactionType getType() {
        return type;
    }
}
