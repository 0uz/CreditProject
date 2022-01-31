package com.payten.credit.controller.credit;

import com.payten.credit.service.credit.Credit;
import com.payten.credit.service.credit.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    @PostMapping("/credit-apply/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public CreditApplyResponse creditApply(@PathVariable Long userId){
        Credit credit = creditService.applyCredit(userId);
        return CreditApplyResponse.convertFrom(credit);
    }

}
