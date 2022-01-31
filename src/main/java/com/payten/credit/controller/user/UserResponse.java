package com.payten.credit.controller.user;

import com.payten.credit.service.user.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {
    private String name;
    private String surname;
    private Long identificationNo;
    private String phoneNo;
    private Double monthlyIncome;

    public static UserResponse convertTo(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .identificationNo(user.getIdentificationNo())
                .phoneNo(user.getPhoneNo())
                .monthlyIncome(user.getMonthlyIncome())
                .build();
    }
}
