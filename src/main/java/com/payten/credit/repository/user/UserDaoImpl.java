package com.payten.credit.repository.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao{

    private final UserJpaRepository userJpaRepository;

    @Override
    public Long create(UserEntity user) {
        return userJpaRepository.save(user).getId();
    }

    @Override
    public Optional<UserEntity> retrieve(Long id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Long update(UserEntity user) {
        return null;
    }
}
