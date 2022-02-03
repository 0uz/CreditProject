package com.payten.credit.service.user;

import com.payten.credit.exception.DataNotFoundException;
import com.payten.credit.exception.ExceptionType;
import com.payten.credit.exception.ValidationException;
import com.payten.credit.repository.user.UserDao;
import com.payten.credit.repository.user.UserEntity;
import com.payten.credit.service.creditscore.FakeCreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserDao userDao;
    private final FakeCreditService fakeCreditService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Long create(User user) {
        // Assume that getting credit score from service
        if (userDao.isIdentificationExist(user.getIdentificationNo())){
            throw new ValidationException(ExceptionType.USER_EXISTS);
        }

        user.setCreditScore(fakeCreditService.getCreditScore(user));
        user.encodePassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userDao.create(user.convertToUserEntity());
    }

    @Override
    public User retrieve(Long id) {
        return userDao.retrieve(id).map(User::convertFrom)
                .orElseThrow(() -> new DataNotFoundException(ExceptionType.USER_DATA_NOT_FOUND,List.of("ID:"+id)));
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    public Long update(User updateUser, Long userId) {
        //TODO
        if (userDao.retrieve(userId).isPresent()){
            UserEntity existingUser = userDao.retrieve(userId).get();
            updateUser.setId(userId);
            updateUser.setCreditScore(existingUser.getCreditScore());
            UserEntity userEntity = existingUser.setModel(updateUser.convertToUserEntity());
            return userDao.create(userEntity);
        }else{
            throw new DataNotFoundException(ExceptionType.USER_DATA_NOT_FOUND, List.of("ID:"+userId));
        }
    }

}
