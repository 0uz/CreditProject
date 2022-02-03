package com.payten.credit.repository.user;

import com.payten.credit.repository.credit.CreditEntity;

import java.util.Optional;

public interface UserDao {
    Long create(UserEntity user);
    Optional<UserEntity> retrieve(Long id);
    void delete(Long id);
}
