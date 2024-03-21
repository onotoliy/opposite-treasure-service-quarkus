package com.github.onotoliy.opposite.treasure.web;

import com.github.onotoliy.opposite.treasure.dto.data.Debt;
import com.github.onotoliy.opposite.treasure.dto.data.DebtSearchParameter;
import com.github.onotoliy.opposite.treasure.dto.data.Deposit;
import com.github.onotoliy.opposite.treasure.dto.data.DepositSearchParameter;
import com.github.onotoliy.opposite.treasure.dto.data.Event;
import com.github.onotoliy.opposite.treasure.dto.data.core.Option;
import com.github.onotoliy.opposite.treasure.dto.data.page.Page;
import com.github.onotoliy.opposite.treasure.web.core.AbstractModifierResource;
import com.github.onotoliy.opposite.treasure.web.core.AbstractReaderResource;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.UUID;

/**
 * WEB сервис чтения долгов.
 *
 * @author Anatoliy Pokhresnyi
 */
@Path(value = "/debt")
@Tag(name = "DebtApi")
public class DebtResource extends AbstractReaderResource<
        Debt,
        DebtSearchParameter> {


}
