package com.ck.cs.config_service.services.validator.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

public class TypeValidatorUtil implements ValidatorUtil {
    private final Set<String> SUPPORTED_TYPES = new HashSet<String>() {{
        add("float");
        add("int");
        add("string");
        add("boolean");
    }};

    @Override
    public ValidationResult isValid(Object object, String parentKey) {
        String type = (String) object;
        if (type.length() == 0) {
            return ValidationResult.builder()
                    .isValid(false)
                    .message(String.format("type should have some value in it key:%s", parentKey))
                    .build();
        }
        TypeAttributes typeAttributes = extractTypeAttributes(type);
        return ValidationResult.builder()
                .isValid(typeAttributes.isValid)
                .message(typeAttributes.message)
                .build();
    }

    @Override
    public ValidationResult isConfigValid(Object validator, Object config, String parentKey) {
        String type = (String) validator;
        try {
            switch (type) {
                case "float": {
                    Float f = (Float) config;
                    break;
                }
                case "int": {
                    Integer i = (Integer) config;
                    break;
                }
                case "string": {
                    String str = (String) config;
                    break;
                }
                case "boolean": {
                    Boolean bool = (Boolean) config;
                    break;
                }
                default: {
                    return ValidationResult.builder()
                            .isValid(false)
                            .message(String.format("key:%s type is unsupported", parentKey))
                            .build();
                }
            }
        } catch (Exception e) {
            return ValidationResult.builder()
                    .isValid(false)
                    .message(String.format("key:%s type is incorrect", parentKey))
                    .build();
        }
        return new ValidationResult();
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    private static class TypeAttributes {
        private Boolean isValid = true;
        private String type;
        private String message;
    }

    private TypeAttributes extractTypeAttributes(String type) {
        String[] strArr = type.split("#");
        if (strArr.length == 0) {
            return TypeAttributes.builder()
                    .isValid(false)
                    .build();
        }
        return getTypeAttribute(strArr);
    }

    private TypeAttributes getTypeAttribute(String[] strArr) {
        if (SUPPORTED_TYPES.contains(strArr[0])) {
            return TypeAttributes.builder()
                    .isValid(true)
                    .type(strArr[0])
                    .build();
        }
        return TypeAttributes.builder()
                .isValid(false)
                .message("unsupported type")
                .build();
    }
}
