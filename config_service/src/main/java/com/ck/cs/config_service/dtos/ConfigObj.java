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
public class ConfigObj {
    @JsonProperty("validator_id")
    private String validatorId;

    @JsonProperty("config")
    private Map<String, Object> config;
}
