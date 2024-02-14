package com.github.onotoliy.opposite.treasure.services.transactions;

import com.github.onotoliy.opposite.treasure.dto.data.Transaction;
import com.github.onotoliy.opposite.treasure.dto.data.TransactionType;
import com.github.onotoliy.opposite.treasure.repositories.DepositRepository;
import com.github.onotoliy.opposite.treasure.utils.GUIDs;

import java.math.BigDecimal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jooq.Configuration;

/**
 * Описание бизнес логики транзакции "Платеж".
 *
 * @author Anatoliy Pokhresnyi
 */
@ApplicationScoped
public class PaidTransactionExecutor
extends AbstractTransactionExecutor {

    /**
     * Репозиторий депозитов.
     */
    private final DepositRepository deposit;

    /**
     * Конструктор.
     *
     * @param deposit Репозиторий депозитов.
     */
    @Inject
    public PaidTransactionExecutor(final DepositRepository deposit) {
        this.deposit = deposit;
    }

    @Override
    public void create(final Configuration configuration,
                       final Transaction dto,
                       final BigDecimal money) {
        deposit.contribution(configuration,
                             GUIDs.parse(dto.person()),
                             money);
    }

    @Override
    public void delete(final Configuration configuration,
                       final Transaction dto,
                       final BigDecimal money) {
        deposit.cost(configuration, GUIDs.parse(dto.person()), money);
    }

    @Override
    public TransactionType type() {
        return TransactionType.PAID;
    }
}
