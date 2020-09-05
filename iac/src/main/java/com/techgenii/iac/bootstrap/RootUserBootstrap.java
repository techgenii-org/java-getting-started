package com.techgenii.iac.bootstrap;

import com.techgenii.iac.entities.UserEntity;
import com.techgenii.iac.repositories.IACRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RootUserBootstrap {

    public static final String ADMIN_USER = "admin";
    public static final String ADMIN_PASSWORD = "veryeasy1";
    @Autowired
    private IACRepository iacRepository;


    @PostConstruct
    private void bootstrapRootUser()  {

        iacRepository.findByUsername(ADMIN_USER).orElseGet(() -> iacRepository.save(UserEntity
                .builder()
                .username(ADMIN_USER)
                .password(ADMIN_PASSWORD).build())
        );
    }
}
