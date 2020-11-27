package com.techgenii.caching.controller;


import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/caching")
public class CachingController {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @PostMapping("/restart")
    public ResponseEntity restartCaching() {
        final Config config = hazelcastInstance.getConfig();
        hazelcastInstance.shutdown();
        hazelcastInstance = Hazelcast.newHazelcastInstance(config);
        return ResponseEntity.ok().build();
    }
}
