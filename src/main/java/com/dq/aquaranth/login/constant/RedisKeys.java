package com.dq.aquaranth.login.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisKeys {
    ROLE_KEY("M_"), USER_KEY("U_");

    private final String key;
}
