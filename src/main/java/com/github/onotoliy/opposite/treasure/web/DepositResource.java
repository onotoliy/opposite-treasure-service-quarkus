package com.github.onotoliy.opposite.treasure.web;

import com.github.onotoliy.opposite.treasure.dto.data.Deposit;
import com.github.onotoliy.opposite.treasure.dto.data.DepositSearchParameter;
import com.github.onotoliy.opposite.treasure.dto.data.Event;
import com.github.onotoliy.opposite.treasure.dto.data.EventSearchParameter;
import com.github.onotoliy.opposite.treasure.dto.data.core.Option;
import com.github.onotoliy.opposite.treasure.dto.data.page.Page;
import com.github.onotoliy.opposite.treasure.web.core.AbstractModifierResource;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.UUID;

/**
 * WEB сервис чтения депозитов.
 *
 * @author Anatoliy Pokhresnyi
 */
@Path(value = "/deposit")
@Tag(name = "DepositApi")
public class DepositResource extends AbstractModifierResource<
        Deposit,
        DepositSearchParameter> {

}
