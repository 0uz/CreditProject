package com.payten.credit.controller.credit;

import com.payten.credit.service.credit.Credit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditResultResponse {
    private Long id;
    private CreditStatus status;
    private Double creditLimit;

    public static CreditResultResponse from(Credit credit) {
        CreditResultResponse resultResponse =  new CreditResultResponse();
        resultResponse.setStatus(credit.getCreditStatus());
        if (credit.getOwner() != null) resultResponse.setId(credit.getOwner().getId());
        resultResponse.setCreditLimit(credit.getCreditStatus().equals(CreditStatus.APPROVED) ? credit.getCreditLimit() : 0);
        return resultResponse;
    }
}
