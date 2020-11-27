package com.techgenii.caching;


import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/*
 * https://www.codeusingjava.com/hazelcast/boot
 * */
@SpringBootApplication
public class CachingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CachingApplication.class,args);
    }

    @Bean
    public Config hazelCastConfig() {
        return new Config().setManagementCenterConfig(
                new ManagementCenterConfig().setEnabled(true).setUrl("http://localhost:8080/hazelcast-mancenter"));
    }

    @Bean
    public HazelcastInstance hazelcastInstance(Config hazelCastConfig) {
        final HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(hazelCastConfig);
        return hazelcastInstance;
    }

}
