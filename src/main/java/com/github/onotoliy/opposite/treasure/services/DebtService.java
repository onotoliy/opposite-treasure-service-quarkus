package com.github.onotoliy.opposite.treasure.services;

import com.github.onotoliy.opposite.treasure.dto.data.Debt;
import com.github.onotoliy.opposite.treasure.dto.data.Deposit;
import com.github.onotoliy.opposite.treasure.dto.data.Event;
import com.github.onotoliy.opposite.treasure.dto.data.core.Option;
import com.github.onotoliy.opposite.treasure.dto.data.page.Page;
import com.github.onotoliy.opposite.treasure.repositories.DebtRepository;

import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Сервис чтения долгов.
 *
 * @author Anatoliy Pokhresnyi
 */
@ApplicationScoped
public class DebtService {

    /**
     * Logger.
     */
    private static final Logger LOGGER =
        LoggerFactory.getLogger(DebtService.class);

    /**
     * Репозиторий.
     */
    private final DebtRepository repository;

    /**
     * Конструктор.
     *
     * @param repository Репозиторий.
     */
    public DebtService(final DebtRepository repository) {
        this.repository = repository;
    }

    /**
     * Получение долгов пользователя.
     *
     * @param person Пользователь.
     * @return Список долгов.
     */
    public Page<Event> getDebts(final UUID person) {
        return repository.getDebts(person);
    }

    /**
     * Получение должников.
     *
     * @param event Событие.
     * @return Список должников.
     */
    public Page<Deposit> getDebtors(final UUID event) {
        return repository.getDebtors(event);
    }

    /**
     * Получение версии сущности.
     *
     * @return Версия сущности.
     */
    public Option version() {
        return repository.version();
    }

    /**
     * Данные, которые необходимо синхронизировать.
     *
     * @param offset Количество записей которое необходимо пропустить.
     * @param numberOfRows Размер страницы.
     * @return Данные, которые необходимо синхронизировать.
     */
    public Page<Debt> sync(final int offset, final int numberOfRows) {
        LOGGER.info("Sync offset {} numberOfRows {}", offset, numberOfRows);

        return repository.sync(offset, numberOfRows);
    }
}
