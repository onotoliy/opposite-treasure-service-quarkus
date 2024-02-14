package com.github.onotoliy.opposite.treasure.services.transactions;

import com.github.onotoliy.opposite.treasure.dto.data.Transaction;
import com.github.onotoliy.opposite.treasure.dto.data.TransactionType;
import com.github.onotoliy.opposite.treasure.exceptions.ModificationException;

import java.math.BigDecimal;

import jakarta.enterprise.context.ApplicationScoped;
import org.jooq.Configuration;

/**
 * Описание бизнес логики транзакции "Не выбрано".
 *
 * @author Anatoliy Pokhresnyi
 */
@ApplicationScoped
public class NoneTransactionExecutor extends AbstractTransactionExecutor {

    /**
     * Конструктор.
     */
    public NoneTransactionExecutor() {

    }

    @Override
    public void create(final Configuration configuration,
                       final Transaction dto,
                       final BigDecimal money) {
        throw new ModificationException("Тип транзакции указан не корректно");
    }

    @Override
    public void delete(final Configuration configuration,
                       final Transaction dto,
                       final BigDecimal money) {
        throw new ModificationException("Тип транзакции указан не корректно");
    }

    @Override
    public TransactionType type() {
        return TransactionType.NONE;
    }
}
