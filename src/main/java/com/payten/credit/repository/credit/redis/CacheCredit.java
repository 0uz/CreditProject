package com.payten.credit.repository.credit.redis;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.payten.credit.controller.credit.CreditStatus;
import com.payten.credit.service.credit.Credit;
import com.payten.credit.service.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CacheCredit {
    private Long id;
    private Double creditLimit;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdDate;

    private CreditStatus creditStatus;
    private User owner;

    public static CacheCredit from(Credit credit) {
        return CacheCredit.builder()
                .id(credit.getId())
                .creditLimit(credit.getCreditLimit())
                .creditStatus(credit.getCreditStatus())
                .owner(credit.getOwner())
                .createdDate(credit.getCreatedDate())
                .build();
    }

    public Credit toModel() {
        return Credit.builder()
                .id(id)
                .creditLimit(creditLimit)
                .creditStatus(creditStatus)
                .owner(owner)
                .createdDate(createdDate)
                .build();
    }
}
