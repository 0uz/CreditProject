package com.payten.credit.service.credit;

import com.payten.credit.controller.credit.CreditStatus;
import com.payten.credit.exception.DataNotFoundException;
import com.payten.credit.exception.ExceptionType;
import com.payten.credit.repository.credit.CreditDao;
import com.payten.credit.repository.credit.CreditEntity;
import com.payten.credit.repository.credit.redis.CreditCache;
import com.payten.credit.repository.user.UserDao;
import com.payten.credit.repository.user.UserEntity;
import com.payten.credit.service.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService{

    private final CreditDao creditDao;
    private final UserDao userDao;
    private final CreditCache creditCache;

    @Override
    public Credit applyCredit(Long userId) {
        UserEntity userEntity = userDao.retrieve(userId)
                .orElseThrow(() -> new DataNotFoundException(ExceptionType.USER_DATA_NOT_FOUND));

        User user = User.convertFrom(userEntity);
        if (user.calculateCreditStatus()){
            CreditEntity credit = new CreditEntity();
            credit.setCreditLimit(user.getCreditLimit());
            credit.setUser(userEntity);
            creditDao.save(credit);
            return Credit.convertFrom(credit, CreditStatus.APPROVED);
        }else{
            return Credit.convertFrom(CreditStatus.REJECTED);
        }
    }

    @Override
    public Credit creditResult(Long identificationNo) {
        Optional<Credit> credit = creditCache.retrieveCredit(identificationNo);
        log.info("Credit is retrieving : {}",identificationNo);

        if (credit.isEmpty()){
            log.info("Credit cache is updating: {}", identificationNo);
            Credit retrievedCredit = creditDao.retrieveByIdentificationNo(identificationNo)
                    .map(creditEntity -> Credit.convertFrom(creditEntity, CreditStatus.APPROVED))
                    .orElseGet(() -> Credit.convertFrom(CreditStatus.REJECTED));
            creditCache.createCredit(retrievedCredit,identificationNo);
            return retrievedCredit;
        }
        return credit.get();
    }
}
