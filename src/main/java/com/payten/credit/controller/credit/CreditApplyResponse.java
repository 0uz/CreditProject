package com.payten.credit.controller.credit;

import com.payten.credit.service.credit.Credit;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreditApplyResponse {
    private CreditStatus creditStatus;
    private Double creditLimit;

    public static CreditApplyResponse convertFrom(Credit applyCredit) {
        return new CreditApplyResponse(applyCredit.getCreditStatus(),applyCredit.getCreditLimit());
    }
}
