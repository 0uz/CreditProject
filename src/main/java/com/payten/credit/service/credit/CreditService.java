package com.payten.credit.service.credit;

import com.payten.credit.controller.credit.CreditStatus;
import com.payten.credit.service.user.User;

public interface CreditService {
    Credit applyCredit(Long userId);
}
