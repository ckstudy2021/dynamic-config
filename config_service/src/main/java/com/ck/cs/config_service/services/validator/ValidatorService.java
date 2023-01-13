package com.ck.cs.config_service.services.validator;

import com.ck.cs.config_service.dtos.ConfigRule;
import org.json.JSONObject;

public interface ValidatorService {
    /***
     *
     * @param configRule
     * @return whether the structure of validator follows the allowed rules or not
     */
    public Boolean verifyValidatorStructure(ConfigRule configRule);

    /***
     *
     * @param validator
     * @param config
     * @return whether the config structure is valid or not with respect to validator
     */
    public Boolean isConfigStructureValid(JSONObject validator, JSONObject config);
}
