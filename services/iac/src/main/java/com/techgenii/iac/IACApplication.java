package com.techgenii.iac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.techgenii.iac.repositories")
@EntityScan(basePackages = "com.techgenii.iac.entities")
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.techgenii"})
public class IACApplication {

    public static void main(String[] args) {
        SpringApplication.run(IACApplication.class,args);
    }
}
