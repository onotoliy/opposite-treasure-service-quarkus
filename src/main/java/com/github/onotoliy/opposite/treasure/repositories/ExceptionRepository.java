package com.github.onotoliy.opposite.treasure.repositories;

import com.github.onotoliy.opposite.treasure.dto.data.core.ExceptionDevice;
import com.github.onotoliy.opposite.treasure.utils.GUIDs;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import org.jooq.DSLContext;

import static com.github.onotoliy.opposite.treasure.jooq.Tables.TREASURE_EXCEPTION;

/**
 * Репозиторий управления ошибками устройства.
 *
 * @author Anatoliy Pokhresnyi
 */
@ApplicationScoped
public class ExceptionRepository {

    /**
     * Контекст подключения к БД.
     */
    private final DSLContext dsl;

    /**
     * Конструктор.
     *
     * @param dsl Контекст подключения к БД.
     */
    public ExceptionRepository(final DSLContext dsl) {
        this.dsl = dsl;
    }

    /**
     * Регистрация ошибки устройства.
     *
     * @param exception Описание ошибки устройства.
     */
    public void registration(final ExceptionDevice exception) {
        dsl.insertInto(TREASURE_EXCEPTION)
           .set(TREASURE_EXCEPTION.GUID, GUIDs.random())
           .set(TREASURE_EXCEPTION.AGENT, exception.agent())
           .set(TREASURE_EXCEPTION.DEVICE, exception.device())
           .set(TREASURE_EXCEPTION.MESSAGE, exception.message())
           .set(TREASURE_EXCEPTION.LOCALIZED_MESSAGE,
                exception.localizedMessage())
           .set(TREASURE_EXCEPTION.STACK_TRACE, exception.stackTrace())
           .execute();
    }

}
