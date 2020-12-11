package com.techgenii.iac.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techgenii.iac.entities.ForgotResetPasswordEntity;
import com.techgenii.iac.entities.UserEntity;
import com.techgenii.iac.entities.UserEntity_;
import com.techgenii.iac.repositories.ForgotResetPasswordRepository;
import com.techgenii.iac.repositories.IACRepository;
import com.techgenii.iac.rqrs.*;
import com.techgenii.iac.utils.JwtUtil;
import com.techgenii.postman.Messenger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class IdentityAndAccessService {


    @Value("${iac.staticpages.baseurl}")
    private String staticPagesBaseUrl;

    @Autowired
    private IACRepository iacRepository;
    @Autowired
    private ForgotResetPasswordRepository forgotResetPasswordRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Messenger messenger;



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

    public Object getMe(String token) {
        return objectMapper.convertValue(jwtUtil.parseToken(token), Map.class).get("sub");
    }

    public Object forgotPassword(String email) {

        if (iacRepository.existsByEmail(email)) {
            ForgotResetPasswordEntity forgotResetPasswordEntity = forgotResetPasswordRepository.findByEmail(email);
            if (forgotResetPasswordEntity == null) {
                forgotResetPasswordEntity = forgotResetPasswordRepository.save(ForgotResetPasswordEntity.builder().uuid(UUID.randomUUID().toString()).email(email).build());
            }

            String resetPasswordLink = generateResetPasswordStaticPageLink(forgotResetPasswordEntity.getUuid());

            HashMap<String, Object> templateVariables = new HashMap<>();
            templateVariables.put("resetPasswordLink",resetPasswordLink);
            messenger.sendEmail(email,"Reset Password","template-thymeleaf", templateVariables);
            log.info(resetPasswordLink);
        }

        return null;
    }

    private String generateResetPasswordStaticPageLink(String uuid) {
        return "https://" + staticPagesBaseUrl + "/reset/password?token=" + uuid;
    }

    public LoginRS resetPassword(ResetPasswordRQ resetPasswordRQ) {

        return forgotResetPasswordRepository.findById(resetPasswordRQ.getForgotPasswordToken()).map(forgotResetPasswordEntity -> {
            forgotResetPasswordRepository.delete(forgotResetPasswordEntity);
            return iacRepository
                    .findByEmail(forgotResetPasswordEntity.getEmail())
                    .map(userEntity -> {
                        userEntity.setPassword(resetPasswordRQ.getNewPassword());
                        iacRepository.save(userEntity);
                        return this.toLoginRS(userEntity);
                    }).orElse(null);
        }).orElseThrow(RuntimeException::new);
    }

    public LoginRS registerUser(RegisterUserRQ registerUserRQ) {
        try{
            return this.toLoginRS(
                    iacRepository.save(UserEntity
                            .builder()
                            .email(registerUserRQ.getEmail())
                            .password(registerUserRQ.getPassword())
                            .username(registerUserRQ.getUsername()).build())
            );

        }catch(DataIntegrityViolationException e){
            Throwable cause = e.getCause().getCause();
            if (cause instanceof SQLIntegrityConstraintViolationException){
                SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException = (SQLIntegrityConstraintViolationException) cause;
                if (sqlIntegrityConstraintViolationException.getSQLState().equals("23000")) {
                    throw new RuntimeException("Duplicate Username or email");
                }
            }

            throw e;

        }
    }
}
