package com.github.onotoliy.opposite.treasure.dto.data;


import com.github.onotoliy.opposite.treasure.dto.data.core.HasName;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasUUID;

import java.util.Set;
import java.util.UUID;

public record User(
        UUID uuid,
        String name,
        String login,
        String email,
        String phone,
        boolean notifyByPhone,
        boolean notifyByEmail,
        Set<String> roles
) implements HasUUID, HasName {

}
