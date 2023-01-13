package com.ck.cs.config_service.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("validator")
public class ValidatorController {
    @GetMapping(value = "/{validatorId}")
    public ResponseEntity<String> getValidatorFromId(@PathVariable("validatorId") String validatorId) {
        log.info(String.format("get validator by Id request received for id:%s", validatorId));
        return null;
    }
}
