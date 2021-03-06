package com.payten.credit.controller.credit;

import com.payten.credit.service.credit.Credit;
import com.payten.credit.service.credit.CreditService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CreditController {

    private final CreditService creditService;


    @PostMapping("/credit-apply/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public CreditApplyResponse creditApply(@PathVariable Long userId){
        Credit credit = creditService.applyCredit(userId);
        return CreditApplyResponse.convertFrom(credit);
    }

    @GetMapping("/credit")
    @ResponseStatus(HttpStatus.OK)
    public CreditResultResponse creditResult(@RequestParam Long identificationNo){
        Credit credit = creditService.creditResult(identificationNo);
        return CreditResultResponse.from(credit);
    }

}
