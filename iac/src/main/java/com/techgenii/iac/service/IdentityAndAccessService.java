package com.techgenii.iac.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.techgenii.iac.entities.UserEntity;
import com.techgenii.iac.repositories.IACRepository;
import com.techgenii.iac.rqrs.LoginRQ;
import com.techgenii.iac.rqrs.LoginRS;
import com.techgenii.iac.rqrs.TokenDTO;
import com.techgenii.iac.rqrs.TokenSubject;
import com.techgenii.iac.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdentityAndAccessService {


    @Autowired
    private IACRepository iacRepository;

    @Autowired
    private JwtUtil jwtUtil;



    public LoginRS login(LoginRQ loginRQ) {
        return iacRepository
                .findByUsernameAndPassword(loginRQ.getUsername(), loginRQ.getPassword())
                .map(this::toLoginRS)
                .orElseThrow(RuntimeException::new);
    }

    private LoginRS toLoginRS(UserEntity userEntity) {
        try {
            TokenDTO tokenDTO = jwtUtil.generateToken(TokenSubject.builder().username(userEntity.getUsername()).build());
            return LoginRS
                    .builder()
                    .token(tokenDTO.getToken())
                    .expiry(tokenDTO.getExpiry())
                    .build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
