package com.github.onotoliy.opposite.treasure.repositories;

import com.github.onotoliy.opposite.treasure.dto.data.core.Option;
import com.github.onotoliy.opposite.treasure.dto.Delivery;
import com.github.onotoliy.opposite.treasure.dto.Notification;
import com.github.onotoliy.opposite.treasure.dto.NotificationSearchParameter;
import com.github.onotoliy.opposite.treasure.dto.NotificationType;
import com.github.onotoliy.opposite.treasure.jooq.tables.TreasureNotification;
import com.github.onotoliy.opposite.treasure.jooq.tables.records.TreasureNotificationRecord;
import com.github.onotoliy.opposite.treasure.repositories.core.AbstractModifierRepository;
import com.github.onotoliy.opposite.treasure.rpc.KeycloakRPC;
import com.github.onotoliy.opposite.treasure.utils.Dates;
import com.github.onotoliy.opposite.treasure.utils.GUIDs;
import com.github.onotoliy.opposite.treasure.utils.Strings;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.OrderField;
import org.jooq.Record;
import org.jooq.UpdateSetMoreStep;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static com.github.onotoliy.opposite.treasure.jooq.Tables.TREASURE_NOTIFICATION;

/**
 * Репозиторий управления уведомлениями.
 *
 * @author Anatoliy Pokhresnyi
 */
@ApplicationScoped
public class NotificationRepository
extends AbstractModifierRepository<
    Notification,
    NotificationSearchParameter,
    TreasureNotificationRecord,
    TreasureNotification> {

    /**
     * Конструктор.
     *
     * @param dsl Контекст подключения к БД.
     * @param user Сервис чтения пользователей.
     */
    NotificationRepository(final DSLContext dsl,
                           final KeycloakRPC user) {
        super(
            TREASURE_NOTIFICATION,
            TREASURE_NOTIFICATION.GUID,
            TREASURE_NOTIFICATION.NAME,
            TREASURE_NOTIFICATION.AUTHOR,
            TREASURE_NOTIFICATION.CREATION_DATE,
            TREASURE_NOTIFICATION.DELETION_DATE,
            dsl,
            user
        );
    }

    /**
     * Уведомление доставленно.
     *
     * @param uuid Уникальный идентификатор.
     */
    public void delivered(final UUID uuid) {
        dsl.update(TREASURE_NOTIFICATION)
           .set(TREASURE_NOTIFICATION.DELIVERY_DATE, Instant.now())
           .where(TREASURE_NOTIFICATION.GUID.eq(uuid))
           .execute();
    }

    /**
     * Сброс уведомлений по указанному типу которые еще не доставленны.
     *
     * @param type Тип уведомления.
     */
    public void discharge(final NotificationType type) {
        dsl.update(TREASURE_NOTIFICATION)
           .set(TREASURE_NOTIFICATION.DELETION_DATE, Instant.now())
           .set(TREASURE_NOTIFICATION.DELIVERY_DATE, Instant.now())
           .where(
                   TREASURE_NOTIFICATION.DELETION_DATE.isNull(),
                   TREASURE_NOTIFICATION.DELIVERY_DATE.isNull(),
                   TREASURE_NOTIFICATION.NOTIFICATION_TYPE.eq(type.name())
                )
           .execute();
    }

    @Override
    public List<Condition> where(final NotificationSearchParameter parameter) {
        List<Condition> conditions = super.where(parameter);

        if (parameter.hasDelivery()) {
            if (parameter.getDelivery() == Delivery.ONLY_DELIVERED) {
                conditions.add(
                    TREASURE_NOTIFICATION.DELIVERY_DATE.isNotNull()
                );
            }

            if (parameter.getDelivery() == Delivery.ONLY_NOT_DELIVERED) {
                conditions.add(
                    TREASURE_NOTIFICATION.DELIVERY_DATE.isNull()
                );
            }
        }

        if (parameter.hasType()) {
            conditions.add(
                TREASURE_NOTIFICATION.NOTIFICATION_TYPE
                    .eq(parameter.getType().name())
            );
        }

        return conditions;
    }

    @Override
    public InsertSetMoreStep<TreasureNotificationRecord> insertQuery(
        final Configuration configuration,
        final Notification dto) {
        return super.insertQuery(configuration, dto)
                    .set(table.MESSAGE, dto.message())
                    .set(table.EXECUTOR, dto.executor())
                    .set(
                        table.NOTIFICATION_TYPE,
                        dto.notificationType().name()
                    )
                    .set(
                        table.DELIVERY_DATE,
                        dto.deliveryDate()
                    );
    }

    @Override
    public UpdateSetMoreStep<TreasureNotificationRecord> updateQuery(
        final Configuration configuration,
        final Notification dto) {
        return super.updateQuery(configuration, dto)
                    .set(table.MESSAGE, dto.message())
                    .set(table.EXECUTOR, dto.executor())
                    .set(
                        table.NOTIFICATION_TYPE,
                        dto.notificationType().name()
                    )
                    .set(
                        table.DELIVERY_DATE,
                        dto.deletionDate()
                    );
    }

    @Override
    protected List<? extends OrderField<?>> orderBy() {
        return new LinkedList<>(Collections.singleton(creationDate.asc()));
    }

    @Override
    protected Notification toDTO(final Record record) {
        return toDTO(record, formatUser(record, author));
    }

    /**
     * Преобзазование записи из БД в объект.
     *
     * @param record Запись из БД.
     * @param author Пользователь.
     * @return Объект.
     */
    public static Notification toDTO(final Record record, final Option author) {
        return new Notification(
                record.getValue(TREASURE_NOTIFICATION.GUID, UUID.class),
                record.getValue(TREASURE_NOTIFICATION.NAME, String.class),
                record.getValue(TREASURE_NOTIFICATION.MESSAGE, String.class),
                NotificationType.valueOf(record.getValue(TREASURE_NOTIFICATION.NOTIFICATION_TYPE, String.class)),
                record.getValue(TREASURE_NOTIFICATION.EXECUTOR, String.class),
                record.getValue(TREASURE_NOTIFICATION.DELIVERY_DATE, Instant.class),
                record.getValue(TREASURE_NOTIFICATION.CREATION_DATE, Instant.class),
                author,
                record.getValue(TREASURE_NOTIFICATION.DELETION_DATE, Instant.class)
        );
    }
}
