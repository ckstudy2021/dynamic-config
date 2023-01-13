package com.ck.cs.config_service.services.validator.util;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.Iterator;

@Slf4j
public class JsonObjectValidatorUtil implements ValidatorUtil {
    private final ValidatorFactory VALIDATOR_FACTORY = new ValidatorFactory();

    @Override
    public ValidationResult isValid(Object object, String parentKey) {
        JSONObject jsonObject = (JSONObject) object;
        if (jsonObject.length() == 0) {
            return ValidationResult.builder()
                    .isValid(false)
                    .message(String.format(String.format("json object should contain some data, parentKey:%s", parentKey)))
                    .build();
        }
        Iterator<String> keyIterator = jsonObject.keys();
        while (keyIterator.hasNext()) {
            String itrKey = keyIterator.next();
            Object itrVal = jsonObject.get(itrKey);
            String currentKey = getCurrentKey(parentKey, itrKey);
            ValidatorUtil validatorUtil = VALIDATOR_FACTORY.getValidator(itrVal);
            if (validatorUtil == null) {
                log.info(String.format("unable to fetch validator, key:%s", currentKey));
                return ValidationResult.builder()
                        .isValid(false)
                        .message(String.format("unable to fetch validator, key:%s", currentKey))
                        .build();
            }
            if (!validatorUtil.isValid(itrVal, currentKey).getIsValid()) {
                log.info(String.format("validator structure is invalid for key:%s", currentKey));
                return ValidationResult.builder()
                        .isValid(false)
                        .message(String.format("validator structure is invalid for key:%s", currentKey))
                        .build();
            }
        }
        return new ValidationResult();
    }

    @Override
    public ValidationResult isConfigValid(Object validatorObj, Object configObj, String parentKey) {
        if (!(configObj instanceof JSONObject)) {
            return ValidationResult.builder()
                    .isValid(false)
                    .message(String.format(String.format("config with key should be a json parentKey:%s", parentKey)))
                    .build();
        }
        JSONObject validator = (JSONObject) validatorObj;
        JSONObject config = (JSONObject) configObj;
        if (config.length() == 0) {
            return ValidationResult.builder()
                    .isValid(false)
                    .message(String.format(String.format("config object is empty, parentKey:%s", parentKey)))
                    .build();
        }
        Iterator<String> validatorItr = validator.keys();
        while (validatorItr.hasNext()) {
            String itrKey = validatorItr.next();
            Object itrVal = validator.get(itrKey);
            String currentKey = getCurrentKey(parentKey, itrKey);
            if (!config.has(itrKey)) {
                return ValidationResult.builder()
                        .isValid(false)
                        .message(String.format(String.format("config object does not have key:%s", currentKey)))
                        .build();
            }
            ValidatorUtil validatorUtil = VALIDATOR_FACTORY.getValidator(itrVal);
            if (validatorUtil == null) {
                return ValidationResult.builder()
                        .isValid(false)
                        .message(String.format("unable to fetch validator for config validation, key:%s", currentKey))
                        .build();
            }
            Object configItrVal = config.get(itrKey);
            if (!validatorUtil.isConfigValid(itrVal, configItrVal, currentKey).getIsValid()) {
                log.info(String.format("config structure is invalid for key:%s", currentKey));
                return ValidationResult.builder()
                        .isValid(false)
                        .message(String.format("config structure is invalid for key:%s", currentKey))
                        .build();
            }
        }
        return new ValidationResult();
    }
}
