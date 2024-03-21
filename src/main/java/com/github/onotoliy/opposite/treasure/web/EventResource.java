package com.github.onotoliy.opposite.treasure.web;

import com.github.onotoliy.opposite.treasure.dto.data.Event;
import com.github.onotoliy.opposite.treasure.dto.data.EventSearchParameter;
import com.github.onotoliy.opposite.treasure.web.core.AbstractModifierResource;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * WEB сервис управления событиями.
 *
 * @author Anatoliy Pokhresnyi
 */
@Path(value = "/event")
@Tag(name = "EventApi")
public class EventResource
extends AbstractModifierResource<
        Event,
    EventSearchParameter> {

}
