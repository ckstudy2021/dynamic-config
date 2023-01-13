package com.ck.cs.config_service.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigRule {
    @JsonProperty("validator")
    private Map<String, Object> validator;

    @JsonProperty("meta")
    private Meta meta;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Meta {

        @JsonProperty("is_multiple_allowed")
        private Boolean multipleAllowed = true;
    }
}
