package com.payten.credit.repository.user;

import com.payten.credit.service.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByIdentificationNo(Long identificationNo);
    UserEntity getByIdentificationNo(Long identificationNo);
}
