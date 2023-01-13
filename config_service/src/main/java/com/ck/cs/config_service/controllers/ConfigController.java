package com.ck.cs.config_service.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("config")
public class ConfigController {
    @GetMapping("/{configId}")
    public ResponseEntity<String> getConfigById(@PathVariable("configId") String configId) {

        return null;
    }
}
