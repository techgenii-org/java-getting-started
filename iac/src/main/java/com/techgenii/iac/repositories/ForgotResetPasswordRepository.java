package com.techgenii.iac.repositories;

import com.techgenii.iac.entities.ForgotResetPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotResetPasswordRepository extends JpaRepository<ForgotResetPasswordEntity, String> {

    ForgotResetPasswordEntity findByEmail(String email);
}
