package com.payten.credit.repository.user;

import java.util.Optional;

public interface UserDao {
    Long create(UserEntity user);
    Optional<UserEntity> retrieve(Long id);
    void delete(Long id);
    Long update(UserEntity user);
}
