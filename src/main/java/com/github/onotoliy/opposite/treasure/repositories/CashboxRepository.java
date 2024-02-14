package com.github.onotoliy.opposite.treasure.repositories;

import com.github.onotoliy.opposite.treasure.dto.data.Cashbox;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.DSL;

import static com.github.onotoliy.opposite.treasure.jooq.Tables.*;

/**
 * Репозиторий управления данными о кассе.
 *
 * @author Anatoliy Pokhresnyi
 */
@ApplicationScoped
public class CashboxRepository {

    /**
     * Контекст подключения к БД.
     */
    private final DSLContext dsl;

    /**
     * Конструктор.
     *
     * @param dsl Контекст подключения к БД.
     */
    public CashboxRepository( final DSLContext dsl) {
        this.dsl = dsl;
    }

    /**
     * Получение данных о кассе.
     *
     * @return Данные о кассе.
     */
    public Cashbox find() {
        return dsl.select().from(TREASURE_CASHBOX).fetchAny(this::toDTO);
    }

    /**
     * Получение суммы денежных средств в кассе.
     *
     * @return Сумма денежных средств в кассе.
     */
    public BigDecimal money() {
        return dsl.select()
                  .from(TREASURE_CASHBOX)
                  .fetchAny(record -> record.getValue(TREASURE_CASHBOX.DEPOSIT,
                                                      BigDecimal.class));
    }

    /**
     * Уменьшение депозита кассы на указанную сумму.
     *
     * @param configuration Настройки транзакции.
     * @param money Денежные средства.
     */
    public void cost(final Configuration configuration,
                     final BigDecimal money) {
        setDeposit(configuration,
                   TREASURE_CASHBOX.DEPOSIT.cast(BigDecimal.class).sub(money));
    }

    /**
     * Увеличение депозита кассы на указанную сумму.
     *
     * @param configuration Настройки транзакции.
     * @param money Денежные средства.
     */
    public void contribution(final Configuration configuration,
                             final BigDecimal money) {
        setDeposit(configuration,
                   TREASURE_CASHBOX.DEPOSIT.cast(BigDecimal.class).add(money));
    }

    /**
     * Произведение операции с депозитом кассы.
     *
     * @param configuration Настройки транзакции.
     * @param deposit Операция над депозитом.
     */
    private void setDeposit(final Configuration configuration,
                            final Field<BigDecimal> deposit) {
        setVersion(configuration);

        DSL.using(configuration)
           .update(TREASURE_CASHBOX)
           .set(TREASURE_CASHBOX.LAST_UPDATE_DATE, Instant.now())
           .set(TREASURE_CASHBOX.DEPOSIT, deposit)
           .execute();
    }

    /**
     * Преобзазование записи из БД в объект.
     *
     * @param record Запись из БД.
     * @return Объект.
     */
    private Cashbox toDTO(final Record record) {
        return new Cashbox(
                record.getValue(TREASURE_CASHBOX.DEPOSIT, BigDecimal.class),
                record.getValue(TREASURE_CASHBOX.LAST_UPDATE_DATE, Instant.class)
        );
    }

    /**
     * Изменение версии справочника.
     *
     * @param configuration Настройка транзакции.
     */
    private void setVersion(
        final Configuration configuration
    ) {
        BigDecimal version = BigDecimal.valueOf(Instant.now().toEpochMilli());

        DSL.using(configuration)
           .update(TREASURE_VERSION)
           .set(TREASURE_VERSION.VERSION, version)
           .where(TREASURE_VERSION.NAME.eq(TREASURE_DEPOSIT.getName()))
           .execute();
    }
}
