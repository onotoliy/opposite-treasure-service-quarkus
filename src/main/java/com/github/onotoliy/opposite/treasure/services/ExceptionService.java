package com.github.onotoliy.opposite.treasure.services;

import com.github.onotoliy.opposite.treasure.dto.data.core.ExceptionDevice;
import com.github.onotoliy.opposite.treasure.repositories.ExceptionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Сервис управления ошибками устройства.
 *
 * @author Anatoliy Pokhresnyi
 */
@ApplicationScoped
public class ExceptionService implements IExceptionService {

    /**
     * Репозиторий ошибок устройств.
     */
    private final ExceptionRepository repository;

    /**
     * Конструктор.
     *
     * @param repository Репозиторий ошибок устройств.
     */
    public ExceptionService(final ExceptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registration(final ExceptionDevice exception) {
        repository.registration(exception);
    }
}
