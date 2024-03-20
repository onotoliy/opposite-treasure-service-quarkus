package com.github.onotoliy.opposite.treasure.web;

import com.github.onotoliy.opposite.treasure.dto.data.TransactionSearchParameter;
import com.github.onotoliy.opposite.treasure.dto.data.Transaction;
import com.github.onotoliy.opposite.treasure.dto.data.TransactionType;
import com.github.onotoliy.opposite.treasure.dto.data.page.Page;
import com.github.onotoliy.opposite.treasure.web.core.AbstractModifierResource;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import java.util.UUID;

/**
 * WEB сервис управления транзакциями.
 *
 * @author Anatoliy Pokhresnyi
 */
@Path(value = "/transaction")
public class TransactionResource
extends AbstractModifierResource<
        Transaction,
    TransactionSearchParameter> {

}
