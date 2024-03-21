package com.github.onotoliy.opposite.treasure.web;

import com.github.onotoliy.opposite.treasure.dto.data.Cashbox;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * WEB сервис чтения данных о кассе.
 *
 * @author Anatoliy Pokhresnyi
 */
@Path(value = "/cashbox")
@Tag(name = "CashboxApi")
public class CashboxResource {


    /**
     * Конструктор.
     *
     */
    @Inject
    public CashboxResource() {

    }

    /**
     * Получение данных о кассе.
     *
     * @return Данные о кассе.
     */
    @GET
    public Cashbox get() {
        return null;
    }
}
