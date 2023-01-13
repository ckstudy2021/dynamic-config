package com.ck.cs.config_service.services.validator;

import com.ck.cs.config_service.dtos.ConfigRule;
import com.ck.cs.config_service.services.validator.util.ValidatorFactory;
import com.ck.cs.config_service.services.validator.util.ValidatorUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service("ValidatorService")
public class ValidatorServiceImpl implements ValidatorService {
    private static final ValidatorFactory VALIDATOR_FACTORY = new ValidatorFactory();

    public Boolean verifyValidatorStructure(ConfigRule configRule) {
        String parentKey = "";
        JSONObject validator = new JSONObject(configRule.getValidator());
        ValidatorUtil.ValidationResult validationResult = VALIDATOR_FACTORY.getValidator(validator).isValid(validator, parentKey);
        return validationResult.getIsValid();
    }

    public Boolean isConfigStructureValid(JSONObject validator, JSONObject config) {
        String parentKey = "";
        ValidatorUtil.ValidationResult validationResult = VALIDATOR_FACTORY.getValidator(validator).isConfigValid(validator, config, parentKey);
        return validationResult.getIsValid();
    }
}