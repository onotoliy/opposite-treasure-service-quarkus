package com.github.onotoliy.opposite.treasure.convectors;

import com.github.onotoliy.opposite.treasure.dto.data.Event;
import com.github.onotoliy.opposite.treasure.utils.Dates;

/**
 * Класс описывающий логику преобразования событие в текстовое уведомление.
 *
 * @author Anatoliy Pokhresnyi
 */
public class EventNotificationConvector
extends AbstractNotificationConvector<Event> {

    /**
     * Конструктор.
     *
     * @param html Использовать HTML верстку.
     */
    public EventNotificationConvector(final boolean html) {
        super(html);
    }

    @Override
    protected void append(final Event dto) {
        append("Мероприятие", dto.name());
        append("Сумма взноса", dto.contribution());
        append("Сдать до", Dates.toShortFormat(dto.deadline()));
    }

}
