package com.microservicesdemo.updatesservice;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

public class AppUtils {
    public static <T> boolean validate(T input) {
        var mappedInput = new ObjectMapper().convertValue(input, Map.class);
        var keys = input.getClass().getDeclaredFields();
        for (Field key : keys) {
            if (Objects.isNull(mappedInput.get(key.getName()))) {
                return false;
            }
        }
        return true;
    }
}

