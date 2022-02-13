package com.payten.credit.service.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.payten.credit.repository.user.UserEntity;
import lombok.*;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private Long Id;
    private String name;
    private String surname;
    private Long identificationNo;
    private String phoneNo;
    private Integer creditScore;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdDate;

    private Double monthlyIncome;
    private Double creditLimit;
    private String password;

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
                .password(retrievedUser.getPassword())
                .build();
    }

    public UserEntity convertToUserEntity() {
        UserEntity user = new UserEntity();
        user.setId(Id);
        user.setName(name);
        user.setSurname(surname);
        user.setIdentificationNo(identificationNo);
        user.setPhoneNo(phoneNo);
        user.setCreatedDate(createdDate);
        user.setMonthlyIncome(monthlyIncome);
        user.setCreditScore(creditScore);
        user.setPassword(password);
        user.setCreatedDate(createdDate);
        return user;
    }

    public void encodePassword(String password) {
        this.password = password;
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


