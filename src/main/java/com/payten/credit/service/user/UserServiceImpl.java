package com.payten.credit.service.user;

import com.payten.credit.exception.DataNotFoundException;
import com.payten.credit.exception.ExceptionType;
import com.payten.credit.repository.user.UserDao;
import com.payten.credit.service.creditscore.FakeCreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserDao userDao;
    private final FakeCreditService fakeCreditService;


    @Override
    public Long create(User user) {
        // Assume that getting credit score from service
        user.setCreditScore(fakeCreditService.getCreditScore(user));
        return userDao.create(user.convertToUserEntity());
    }

    @Override
    public User retrieve(Long id) {
        return userDao.retrieve(id).map(User::convertFrom)
                .orElseThrow(() -> new DataNotFoundException(ExceptionType.USER_DATA_NOT_FOUND));
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Long update(User user, Long userId) {
        return userDao.update(user.convertToUserEntity(),userId);
    }

}
