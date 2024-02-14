package com.github.onotoliy.opposite.treasure.repositories;

import com.github.onotoliy.opposite.treasure.dto.data.Event;
import com.github.onotoliy.opposite.treasure.dto.data.core.Option;
import com.github.onotoliy.opposite.treasure.dto.EventSearchParameter;
import com.github.onotoliy.opposite.treasure.jooq.tables.TreasureEvent;
import com.github.onotoliy.opposite.treasure.jooq.tables.records.TreasureEventRecord;
import com.github.onotoliy.opposite.treasure.repositories.core.AbstractModifierRepository;
import com.github.onotoliy.opposite.treasure.rpc.KeycloakRPC;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.Record;
import org.jooq.UpdateSetMoreStep;

import static com.github.onotoliy.opposite.treasure.jooq.Tables.TREASURE_EVENT;

/**
 * Репозиторий управления событиями.
 *
 * @author Anatoliy Pokhresnyi
 */
@ApplicationScoped
public class EventRepository
extends AbstractModifierRepository<
        Event,
    EventSearchParameter,
    TreasureEventRecord,
    TreasureEvent> {

    /**
     * Конструктор.
     *
     * @param dsl Контекст подключения к БД.
     * @param user Сервис чтения пользователей.
     */
    public EventRepository(final DSLContext dsl, final KeycloakRPC user) {
        super(
            TREASURE_EVENT,
            TREASURE_EVENT.GUID,
            TREASURE_EVENT.NAME,
            TREASURE_EVENT.AUTHOR,
            TREASURE_EVENT.CREATION_DATE,
            TREASURE_EVENT.DELETION_DATE,
            dsl,
            user);
    }

    @Override
    public List<Condition> where(final EventSearchParameter parameter) {
        List<Condition> conditions = super.where(parameter);

        if (parameter.hasName()) {
            conditions.add(TREASURE_EVENT.NAME.likeIgnoreCase(
                    "%" + parameter.getName() + "%"));
        }

        return conditions;
    }

    @Override
    public InsertSetMoreStep<TreasureEventRecord> insertQuery(
            final Configuration configuration,
            final Event dto) {
        return super.insertQuery(configuration, dto)
                    .set(table.CONTRIBUTION,dto.contribution())
                    .set(table.TOTAL, dto.total())
                    .set(table.DEADLINE, dto.deadline());
    }

    @Override
    public UpdateSetMoreStep<TreasureEventRecord> updateQuery(
            final Configuration configuration,
            final Event dto) {
        return super.updateQuery(configuration, dto)
                    .set(table.CONTRIBUTION, dto.contribution())
                    .set(table.TOTAL, dto.total())
                    .set(table.DEADLINE, dto.deadline());
    }

    @Override
    protected Event toDTO(final Record record) {
        return toDTO(record, formatUser(record, author));
    }

    /**
     * Преобзазование записи из БД в объект.
     *
     * @param record Запись из БД.
     * @param author Пользователь.
     * @return Объект.
     */
    public static Event toDTO(final Record record, final Option author) {
        return new Event(
                record.getValue(TREASURE_EVENT.GUID, UUID.class),
                record.getValue(TREASURE_EVENT.NAME, String.class),
                record.getValue(TREASURE_EVENT.CONTRIBUTION, BigDecimal.class),
                record.getValue(TREASURE_EVENT.TOTAL, BigDecimal.class),
                record.getValue(TREASURE_EVENT.DEADLINE, Instant.class),
                record.getValue(TREASURE_EVENT.CREATION_DATE, Instant.class),
                author,
                record.getValue(TREASURE_EVENT.DELETION_DATE, Instant.class)
        );
    }

    /**
     * Преобразование записи из БД в короткий объект.
     *
     * @param record Запись из БД.
     * @return Объект.
     */
    public static Option toOption(final Record record) {
        return new Option(
                record.getValue(TREASURE_EVENT.GUID, UUID.class).toString(),
                record.getValue(TREASURE_EVENT.NAME, String.class)
        );
    }
}
