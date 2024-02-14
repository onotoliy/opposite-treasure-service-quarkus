package com.github.onotoliy.opposite.treasure.services.transactions;

import com.github.onotoliy.opposite.treasure.dto.data.Transaction;
import com.github.onotoliy.opposite.treasure.dto.data.TransactionType;
import com.github.onotoliy.opposite.treasure.exceptions.ModificationException;
import com.github.onotoliy.opposite.treasure.repositories.DebtRepository;
import com.github.onotoliy.opposite.treasure.repositories.DepositRepository;
import com.github.onotoliy.opposite.treasure.utils.GUIDs;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jooq.Configuration;

/**
 * Описание бизнес логики транзакции "Списания сбаланса".
 *
 * @author Anatoliy Pokhresnyi
 */
@ApplicationScoped
public class WriteOffTransactionExecutor
extends AbstractTransactionExecutor {

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
     * @param deposit Репозиторий депозитов.
     * @param debt Репозиторий долгов.
     */
    @Inject
    public WriteOffTransactionExecutor(final DepositRepository deposit,
                                       final DebtRepository debt) {
        this.deposit = deposit;
        this.debt = debt;
    }

    @Override
    public void create(final Configuration configuration,
                       final Transaction dto,
                       final BigDecimal money) {
        UUID person = GUIDs.parse(dto.person());
        BigDecimal value = deposit.money(person);

        if (value == null || value.compareTo(money) < 0) {
            throw new ModificationException(
                "Депозит не может быть отрицательным");
        }

        this.deposit.cost(configuration, person, money);
        this.debt.contribution(configuration,
                               person,
                               GUIDs.parse(dto.event()));
    }

    @Override
    public void delete(final Configuration configuration,
                       final Transaction dto,
                       final BigDecimal money) {

        this.deposit.contribution(configuration,
                                  GUIDs.parse(dto.person()),
                                  money);
        this.debt.cost(configuration,
                       GUIDs.parse(dto.person()),
                       GUIDs.parse(dto.event()));
    }

    @Override
    public TransactionType type() {
        return TransactionType.WRITE_OFF;
    }
}
