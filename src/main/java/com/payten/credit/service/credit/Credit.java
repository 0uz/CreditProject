package com.payten.credit.service.credit;

import com.payten.credit.controller.credit.CreditStatus;
import com.payten.credit.repository.credit.CreditEntity;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Builder
public class Credit implements Serializable {
    private Long id;
    private Double creditLimit;
    private LocalDateTime createdDate;
    private CreditStatus creditStatus;

    public static Credit convertFrom(CreditEntity credit, CreditStatus status) {
        return Credit.builder()
                .id(credit.getId())
                .creditLimit(credit.getCreditLimit())
                .creditStatus(status)
                .createdDate(credit.getCreatedDate())
                .build();
    }

    public static Credit convertFrom(CreditStatus rejected) {
        return Credit.builder()
                .creditLimit(0D)
                .creditStatus(rejected)
                .build();
    }
}
