package com.payten.credit.service.credit;

import com.payten.credit.controller.credit.CreditStatus;

public interface CreditService {
    Credit applyCredit(Long userId);

    Credit creditResult(Long identificationNo);
}
