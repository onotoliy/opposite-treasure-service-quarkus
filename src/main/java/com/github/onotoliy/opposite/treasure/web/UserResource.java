package com.github.onotoliy.opposite.treasure.web;

import com.github.onotoliy.opposite.treasure.dto.data.User;
import com.github.onotoliy.opposite.treasure.dto.data.core.ExceptionDevice;
import com.github.onotoliy.opposite.treasure.dto.data.core.Option;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * WEB сервис чтения пользователей системы.
 *
 * @author Anatoliy Pokhresnyi
 */
@Path(value = "/user")
public class UserResource {

    /**
     * Logger.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(UserResource.class);

    /**
     * Конструктор.
     */
    @Inject
    public UserResource() {
    }

    /**
     * Получение текущего пользователя.
     *
     * @return Текущий пльзователь.
     */
    @GET
    @Path(value = "/current")
    public Option getCurrentUser() {
        return null;
    }

    /**
     * Получение ролей текущего пользователя.
     *
     * @return Роли.
     */
    @GET
    @Path(value = "/current/roles")
    public Set<String> getCurrentUserRoles() {
        return null;
    }

    /**
     * Получение списка всех пользователей, зарегистрированных в системе.
     *
     * @return Пользователи.
     */
    @GET
    @Path(value = "/list")
    public List<Option> getAll() {
        return null;
    }

    /**
     * Получение списка всех пользователей, зарегистрированных в системе.
     *
     * @return Пользователи.
     */
    @GET
    @Path(value = "/list/full")
    public List<User> getFullDTOAll() {
        return null;
    }

    /**
     * Отправка отчета о долгах.
     */
    @POST
    @Path(value = "/notification")
    public void notification() {
        LOGGER.info("Request to send notifications received.");
    }

    /**
     * Регистрация ошибки на клиенте.
     *
     * @param exception Описание ошибки устройства.
     */
    @POST
    @Path(value = "/register/exception")
    public void registration(final ExceptionDevice exception) {

    }
}
