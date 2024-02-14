package com.github.onotoliy.opposite.treasure.repositories;

import com.github.onotoliy.opposite.treasure.dto.data.Transaction;
import com.github.onotoliy.opposite.treasure.dto.data.TransactionType;
import com.github.onotoliy.opposite.treasure.dto.data.core.Option;
import com.github.onotoliy.opposite.treasure.dto.TransactionSearchParameter;
import com.github.onotoliy.opposite.treasure.jooq.Tables;
import com.github.onotoliy.opposite.treasure.jooq.tables.TreasureTransaction;
import com.github.onotoliy.opposite.treasure.jooq.tables.records.TreasureTransactionRecord;
import com.github.onotoliy.opposite.treasure.repositories.core.AbstractModifierRepository;
import com.github.onotoliy.opposite.treasure.rpc.KeycloakRPC;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.github.onotoliy.opposite.treasure.utils.GUIDs;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.NormalScope;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.OrderField;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.UpdateSetMoreStep;

import static com.github.onotoliy.opposite.treasure.jooq.Tables.TREASURE_TRANSACTION;

/**
 * Репозиторий управления транзакциями.
 *
 * @author Anatoliy Pokhresnyi
 */
@ApplicationScoped
public class TransactionRepository
extends AbstractModifierRepository<
        Transaction,
    TransactionSearchParameter,
    TreasureTransactionRecord,
    TreasureTransaction> {

    /**
     * Конструктор.
     *
     * @param dsl Контекст подключения к БД.
     * @param user Сервис чтения пользователей.
     */
    public TransactionRepository(final DSLContext dsl, final KeycloakRPC user) {
        super(
            TREASURE_TRANSACTION,
            TREASURE_TRANSACTION.GUID,
            TREASURE_TRANSACTION.NAME,
            TREASURE_TRANSACTION.AUTHOR,
            TREASURE_TRANSACTION.CREATION_DATE,
            TREASURE_TRANSACTION.DELETION_DATE,
            dsl,
            user);
    }

    @Override
    protected SelectJoinStep<Record> findQuery() {
        return super.findQuery()
                    .leftJoin(Tables.TREASURE_EVENT)
                    .on(Tables.TREASURE_EVENT.GUID.eq(table.EVENT_GUID));
    }

    @Override
    protected List<Condition> where(
            final TransactionSearchParameter parameter) {
        List<Condition> conditions = super.where(parameter);

        if (parameter.hasEvent()) {
            conditions.add(
                TREASURE_TRANSACTION.EVENT_GUID.eq(parameter.getEvent()));
        }

        if (parameter.hasUser()) {
            conditions.add(
                TREASURE_TRANSACTION.USER_GUID.eq(parameter.getUser()));
        }

        if (parameter.hasName()) {
            conditions.add(
                TREASURE_TRANSACTION.NAME.likeIgnoreCase(
                        "%" + parameter.getName() + "%"));
        }

        if (parameter.hasType()) {
            conditions.add(
                TREASURE_TRANSACTION.TYPE.eq(parameter.getType().name()));
        }

        return conditions;
    }

    @Override
    public InsertSetMoreStep<TreasureTransactionRecord> insertQuery(
            final Configuration configuration,
            final Transaction dto) {
        return super.insertQuery(configuration, dto)
                    .set(table.CASH, dto.cash())
                    .set(table.TRANSACTION_DATE, dto.transactionDate())
                    .set(table.USER_GUID, GUIDs.parse(dto.person()))
                    .set(table.EVENT_GUID, GUIDs.parse(dto.event()))
                    .set(table.TYPE, dto.type().name());
    }

    @Override
    public UpdateSetMoreStep<TreasureTransactionRecord> updateQuery(
            final Configuration configuration,
            final Transaction dto) {
        return super.updateQuery(configuration, dto)
                    .set(table.CASH, dto.cash())
                    .set(table.TRANSACTION_DATE,
                         dto.transactionDate())
                    .set(table.USER_GUID, GUIDs.parse(dto.person()))
                    .set(table.EVENT_GUID, GUIDs.parse(dto.event()))
                    .set(table.TYPE, dto.type().name());
    }

    @Override
    protected List<? extends OrderField<?>> orderBy() {
        return new LinkedList<>(
            Collections.singleton(table.TRANSACTION_DATE.desc()));
    }

    @Override
    protected Transaction toDTO(final Record record) {
        Option person = record.getValue(table.USER_GUID, UUID.class) == null
            ? null
            : formatUser(record, table.USER_GUID);

        return new Transaction(
            record.getValue(table.GUID, UUID.class),
            record.getValue(table.NAME, String.class),
            record.getValue(table.CASH, BigDecimal.class),
            TransactionType.valueOf(record.getValue(table.TYPE, String.class)),
            person,
            EventRepository.toOption(record),
            record.getValue(table.TRANSACTION_DATE, Instant.class),
            record.getValue(table.TRANSACTION_DATE, Instant.class),
            formatUser(record, author),
            record.getValue(table.DELETION_DATE, Instant.class)
        );
    }
}
