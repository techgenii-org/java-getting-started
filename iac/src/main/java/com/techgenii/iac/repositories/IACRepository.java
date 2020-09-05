package com.techgenii.iac.repositories;

import com.techgenii.iac.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IACRepository extends JpaRepository<UserEntity, Long > {

    Optional<UserEntity> findByUsernameAndPassword(String username, String password);

    Optional<UserEntity> findByUsername(String username);
}
