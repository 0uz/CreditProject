package com.payten.credit.service.user;

import com.payten.credit.repository.user.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class User {
    private Long Id;
    private String name;
    private String surname;
    private Long identificationNo;
    private String phoneNo;
    private Integer creditScore;
    private LocalDateTime createdDate;
    private Double monthlyIncome;
    private Double creditLimit;

    private final Double CREDIT_MULTIPLIER = 4D;

    public static User convertFrom(UserEntity retrievedUser) {
        return User.builder()
                .Id(retrievedUser.getId())
                .name(retrievedUser.getName())
                .surname(retrievedUser.getSurname())
                .identificationNo(retrievedUser.getIdentificationNo())
                .phoneNo(retrievedUser.getPhoneNo())
                .createdDate(retrievedUser.getCreatedDate())
                .monthlyIncome(retrievedUser.getMonthlyIncome())
                .creditScore(retrievedUser.getCreditScore())
                .build();
    }

    public UserEntity convertToUserEntity() {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setSurname(surname);
        user.setIdentificationNo(identificationNo);
        user.setPhoneNo(phoneNo);
        user.setCreatedDate(createdDate);
        user.setMonthlyIncome(monthlyIncome);
        user.setCreditScore(creditScore);
        return user;
    }

    // credit limit < 500 REJECTED
    // credit limit < 1000 income < 5000 : 10k ? 20k
    // credit limit >= 1000 income * 4
    public boolean calculateCreditStatus() {
        if (creditScore < 500){
            return false;
        }else if(creditScore < 1000){
            if (monthlyIncome < 5000){
                creditLimit = 10000D;
            }else{
                creditLimit = 20000D;
            }
            return true;
        }else{
            creditLimit = monthlyIncome * CREDIT_MULTIPLIER;
            return true;
        }
    }

}


