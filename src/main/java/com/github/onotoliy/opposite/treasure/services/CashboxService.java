package com.github.onotoliy.opposite.treasure.services;

import com.github.onotoliy.opposite.treasure.dto.data.Cashbox;
import com.github.onotoliy.opposite.treasure.repositories.CashboxRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


/**
 * Сервис чтения данных о кассе.
 *
 * @author Anatoliy Pokhresnyi
 */
@ApplicationScoped
public class CashboxService implements ICashboxService {

    /**
     * Репозиторий.
     */
    private final CashboxRepository repository;

    /**
     * Конструтор.
     *
     * @param repository Репозиторий.
     */
    public CashboxService(final CashboxRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cashbox get() {
        return repository.find();
    }
}
