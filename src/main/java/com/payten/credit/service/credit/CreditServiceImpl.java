package com.payten.credit.service.credit;

import com.payten.credit.controller.credit.CreditStatus;
import com.payten.credit.exception.DataNotFoundException;
import com.payten.credit.exception.ExceptionType;
import com.payten.credit.repository.credit.CreditDao;
import com.payten.credit.repository.credit.CreditEntity;
import com.payten.credit.repository.user.UserDao;
import com.payten.credit.repository.user.UserEntity;
import com.payten.credit.service.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService{

    private final CreditDao creditDao;
    private final UserDao userDao;

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
       return creditDao.retrieveByIdentificationNo(identificationNo)
               .map(creditEntity-> Credit.convertFrom(creditEntity,CreditStatus.APPROVED))
               .orElseGet(() -> Credit.convertFrom(CreditStatus.REJECTED));
    }
}
