package com.ck.cs.config_service.services.validator.util;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;

@Slf4j
public class JsonArrayValidatorUtil implements ValidatorUtil {
    private final ValidatorFactory VALIDATOR_FACTORY = new ValidatorFactory();

    @Override
    public ValidationResult isValid(Object object, String parentKey) {
        JSONArray jsonArray = (JSONArray) object;
        if (jsonArray.length() != 1) {
            log.info(String.format("jsonArray length should be 1 only"));
            return ValidationResult.builder()
                    .isValid(false)
                    .message(String.format("jsonArray length should be 1 only"))
                    .build();
        }
        Object obj = jsonArray.get(0);
        ValidatorUtil validatorUtil = VALIDATOR_FACTORY.getValidator(obj);
        if (!validatorUtil.isValid(obj, parentKey).getIsValid()) {
            log.info(String.format("jsonArray obj is invalid"));
            return ValidationResult.builder()
                    .isValid(false)
                    .message(String.format("jsonArray obj is invalid"))
                    .build();
        }
        return new ValidationResult();
    }

    @Override
    public ValidationResult isConfigValid(Object validator, Object config, String parentKey) {
        if (!(config instanceof JSONArray)) {
            return ValidationResult.builder()
                    .isValid(false)
                    .message(String.format(String.format("config with key should be a jsonArray, parentKey:%s", parentKey)))
                    .build();
        }
        JSONArray validatorArr = (JSONArray) validator;
        JSONArray configArr = (JSONArray) config;

        Object obj = validatorArr.get(0);
        ValidatorUtil validatorUtil = VALIDATOR_FACTORY.getValidator(obj);
        for (int i = 0; i < configArr.length(); i++) {
            Object configItr = configArr.get(i);
            if (!validatorUtil.isConfigValid(obj, configItr, parentKey).getIsValid()) {
                return ValidationResult.builder()
                        .isValid(false)
                        .message(String.format("configArr obj is invalid at position %s for key:%s", i, parentKey))
                        .build();
            }
        }
        return new ValidationResult();
    }
}
