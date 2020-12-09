package com.techgenii.caching.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/caching")
@Slf4j
public class CachingController {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/restart")
    public ResponseEntity restartCaching(Config config) throws JsonProcessingException {
        log.info("Restarting hazleCast with config {}",objectMapper.writeValueAsString(config));
        final Config existingConfig = hazelcastInstance.getConfig();
        hazelcastInstance.shutdown();
        hazelcastInstance = Hazelcast.newHazelcastInstance(existingConfig);
        return ResponseEntity.ok().build();
    }
}
