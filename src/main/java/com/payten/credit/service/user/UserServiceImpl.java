package com.payten.credit.service.user;

import com.payten.credit.exception.DataNotFoundException;
import com.payten.credit.exception.ExceptionType;
import com.payten.credit.exception.ValidationException;
import com.payten.credit.repository.user.UserDao;
import com.payten.credit.repository.user.UserEntity;
import com.payten.credit.repository.user.redis.UserCache;
import com.payten.credit.service.creditscore.FakeCreditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserDao userDao;
    private final FakeCreditService fakeCreditService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserCache userCache;

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
        Optional<User> user = userCache.retrieveUser(id);
        log.info("User is retrieving : {}",id);
        if (user.isEmpty()){
            log.info("User cache is updating: {}", id);
            User retrievedUser = userDao.retrieve(id).map(User::convertFrom)
                    .orElseThrow(() -> new DataNotFoundException(ExceptionType.USER_DATA_NOT_FOUND, List.of("ID:" + id)));
            userCache.createUser(retrievedUser);
            return retrievedUser;

        }
        return user.get();
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    public Long update(User updateUser, Long userId) {
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
