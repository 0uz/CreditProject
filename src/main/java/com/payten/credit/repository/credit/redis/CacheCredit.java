package com.payten.credit.repository.credit.redis;

import com.payten.credit.controller.credit.CreditStatus;
import com.payten.credit.service.credit.Credit;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CacheCredit {
    private Long id;
    private Double creditLimit;
//    private LocalDateTime createdDate;
    private CreditStatus creditStatus;

    public static CacheCredit from(Credit credit) {
        return CacheCredit.builder()
                .id(credit.getId())
                .creditLimit(credit.getCreditLimit())
//                .createdDate(credit.getCreatedDate())
                .creditStatus(credit.getCreditStatus())
                .build();
    }

    public Credit toModel() {
        return Credit.builder()
                .id(id)
                .creditLimit(creditLimit)
//                .createdDate(createdDate)
                .creditStatus(creditStatus)
                .build();
    }
}
