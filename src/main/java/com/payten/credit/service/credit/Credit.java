package com.payten.credit.service.credit;

import com.payten.credit.controller.credit.CreditStatus;
import com.payten.credit.repository.credit.CreditEntity;
import com.payten.credit.repository.user.UserEntity;
import com.payten.credit.service.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Builder
@Setter
public class Credit implements Serializable {
    private Long id;
    private Double creditLimit;
    private LocalDateTime createdDate;
    private CreditStatus creditStatus;
    private User owner;

    public static Credit convertFrom(CreditEntity credit, CreditStatus status) {
        return Credit.builder()
                .id(credit.getId())
                .creditLimit(credit.getCreditLimit())
                .creditStatus(status)
                .createdDate(credit.getCreatedDate())
                .owner(User.convertFrom(credit.getUser()))
                .build();
    }

    public static Credit convertFrom(CreditStatus rejected) {
        return Credit.builder()
                .creditLimit(0D)
                .creditStatus(rejected)
                .build();
    }
}
