package com.payten.credit.repository.user;

import com.payten.credit.repository.common.Status;
import com.payten.credit.repository.credit.CreditEntity;
import com.payten.credit.service.user.User;
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
        userJpaRepository.findById(id)
                .ifPresent(user ->{
                    user.setStatus(Status.DELETED);
                    userJpaRepository.save(user);
                });
    }

    @Override
    public boolean isIdentificationExist(Long identificationNo) {
        return userJpaRepository.existsByIdentificationNo(identificationNo);
    }

    @Override
    public User retrieveByIdentificationNo(Long identificationNo) {
        return User.convertFrom(userJpaRepository.getByIdentificationNo(identificationNo));
    }


}
