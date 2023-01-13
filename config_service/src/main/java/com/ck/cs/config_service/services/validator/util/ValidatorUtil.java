package com.ck.cs.config_service.services.validator.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;

public interface ValidatorUtil {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    static class ValidationResult {
        private Boolean isValid = true;
        private String message;
    }

    ValidationResult isValid(Object object, String parentKey);

    ValidationResult isConfigValid(Object validator, Object config, String parentKey);

    default String getCurrentKey(String parentKey, String currKey) {
        if (StringUtils.isEmpty(parentKey)) {
            return currKey;
        }
        return String.format("%s.%s", parentKey, currKey);
    }
}
