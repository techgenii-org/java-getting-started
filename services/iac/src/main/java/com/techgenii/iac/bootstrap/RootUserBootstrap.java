package com.techgenii.iac.bootstrap;

import com.techgenii.iac.entities.UserEntity;
import com.techgenii.iac.repositories.IACRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SuppressWarnings("Bootstrapping class to be used by spring boot at runtime")
@Component
public class RootUserBootstrap {

    public static final String ADMIN_USER = "admin";
    public static final String ADMIN_PASSWORD = "veryeasy1";
    public static final String ADMIN_EMAIL = "techgenii.org@gmail.com";
    @Autowired
    private IACRepository iacRepository;


    @PostConstruct
    private void bootstrapRootUser()  {

        iacRepository.findByUsername(ADMIN_USER).orElseGet(() -> iacRepository.save(UserEntity
                .builder()
                .username(ADMIN_USER)
                .password(ADMIN_PASSWORD)
                .email(ADMIN_EMAIL).build())
        );
    }
}
