package com.payten.credit.service.creditscore;

import com.payten.credit.service.user.User;
import org.springframework.stereotype.Service;

@Service
public class FakeCreditServiceImpl implements FakeCreditService{

    private final Integer creditScore = 500;

    @Override
    public Integer getCreditScore(User user) {
        return creditScore;
    }
}
