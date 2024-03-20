package com.github.onotoliy.opposite.treasure.web;

import com.github.onotoliy.opposite.treasure.dto.data.Event;
import com.github.onotoliy.opposite.treasure.dto.data.EventSearchParameter;
import com.github.onotoliy.opposite.treasure.web.core.AbstractModifierResource;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;

/**
 * WEB сервис управления событиями.
 *
 * @author Anatoliy Pokhresnyi
 */
@Path(value = "/event")
public class EventResource
extends AbstractModifierResource<
        Event,
    EventSearchParameter> {

}
