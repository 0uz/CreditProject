package com.payten.credit.repository.user.redis;

import com.payten.credit.service.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CacheUser {
    private Long Id;
    private String name;
    private String surname;
    private Long identificationNo;
    private String phoneNo;
    private Integer creditScore;
    private Double monthlyIncome;
    private Double creditLimit;
    private String password;

    public static CacheUser from(User user) {
        return CacheUser.builder()
                .Id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .identificationNo(user.getIdentificationNo())
                .phoneNo(user.getPhoneNo())
                .creditScore(user.getCreditScore())
                .creditScore(user.getCreditScore())
                .monthlyIncome(user.getMonthlyIncome())
                .creditLimit(user.getCreditLimit())
                .password(user.getPassword())
                .build();
    }

    public User toModel() {
        return User.builder()
                .Id(Id)
                .name(name)
                .surname(surname)
                .identificationNo(identificationNo)
                .phoneNo(phoneNo)
                .creditScore(creditScore)
                .monthlyIncome(monthlyIncome)
                .creditLimit(creditLimit)
                .password(password)
                .build();
    }


}
