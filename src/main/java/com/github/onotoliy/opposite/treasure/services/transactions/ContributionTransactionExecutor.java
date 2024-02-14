package com.github.onotoliy.opposite.treasure.services.transactions;

import com.github.onotoliy.opposite.treasure.dto.data.Transaction;
import com.github.onotoliy.opposite.treasure.dto.data.TransactionType;
import com.github.onotoliy.opposite.treasure.repositories.CashboxRepository;
import com.github.onotoliy.opposite.treasure.repositories.DebtRepository;
import com.github.onotoliy.opposite.treasure.repositories.DepositRepository;
import com.github.onotoliy.opposite.treasure.utils.GUIDs;
import com.github.onotoliy.opposite.treasure.utils.Objects;

import java.math.BigDecimal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jooq.Configuration;

/**
 * Описание бизнес логики транзакции "Взнос".
 *
 * @author Anatoliy Pokhresnyi
 */
@ApplicationScoped
public class ContributionTransactionExecutor
extends AbstractTransactionExecutor {

    /**
     * Репозиторий данных о кассе.
     */
    private final CashboxRepository cashbox;

    /**
     * Репозиторий депозитов.
     */
    private final DepositRepository deposit;

    /**
     * Репозиторий долгов.
     */
    private final DebtRepository debt;

    /**
     * Конструктор.
     *
     * @param cashbox Репозиторий данных о кассе.
     * @param deposit Репозиторий депозитов.
     * @param debt Репозиторий долгов.
     */
    @Inject
    public ContributionTransactionExecutor(final CashboxRepository cashbox,
                                           final DepositRepository deposit,
                                           final DebtRepository debt) {
        this.cashbox = cashbox;
        this.deposit = deposit;
        this.debt = debt;
    }

    @Override
    public void create(final Configuration configuration,
                       final Transaction dto,
                       final BigDecimal money) {
        if (Objects.nonEmpty(dto.event())) {
            debt.contribution(configuration,
                              GUIDs.parse(dto.person()),
                              GUIDs.parse(dto.event()));
        } else {
            deposit.contribution(
                configuration,
                GUIDs.parse(dto.person()),
                money
            );
        }
        cashbox.contribution(configuration, money);
    }

    @Override
    public void delete(final Configuration configuration,
                       final Transaction dto,
                       final BigDecimal money) {
        if (Objects.nonEmpty(dto.event())) {
            debt.cost(configuration,
                      GUIDs.parse(dto.person()),
                      GUIDs.parse(dto.event()));
        } else {
            deposit.cost(
                configuration,
                GUIDs.parse(dto.person()),
                money
            );
        }
        cashbox.cost(configuration, money);
    }

    @Override
    public TransactionType type() {
        return TransactionType.CONTRIBUTION;
    }
}
