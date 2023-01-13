package com.ck.cs.config_service.services.validator.util;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

@Slf4j
public class ValidatorFactory {

    public ValidatorUtil getValidator(Object object) {
        switch (object) {
            case JSONObject jsonObject -> {
                return new JsonObjectValidatorUtil();
            }
            case JSONArray jsonArray -> {
                return new JsonArrayValidatorUtil();
            }
            case String str -> {
                return new TypeValidatorUtil();
            }
            default -> {
                log.error(String.format("instance of object is unsupported, passed instace:%s", object.getClass().getName()));
            }
        }
        return null;
    }
}
