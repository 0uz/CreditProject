package com.payten.credit.controller.user;

import com.payten.credit.service.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserCreateRequest {

    @NotBlank(message = "Name must not be empty")
    @NotNull(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Surname must not be empty")
    @NotNull(message = "Surname must not be empty")
    private String surname;

    //Identification number validation regex
    @NotNull(message = "Identification number must not be empty")
    @Pattern(regexp="^[1-9][0-9]{10,10}$",message = "Identification number must be match pattern")
    private String identificationNo;

    //Phone number validation regex
    @Pattern(regexp="^$|[0-9]{10}",message = "Phone number must be match pattern")
    private String phoneNo;

    @NotNull(message = "Monthly income must not be empty")
    @Min(value = 0, message = "Monthly income must be positive")
    private Double monthlyIncome;

    @NotNull(message = "Password must not be empty")
    @Length(min = 3,max = 8, message = "Password must be 3-8 character")
    private String password;

    public User toModel() {
        return User.builder()
                .name(name)
                .surname(surname)
                .identificationNo(Long.parseLong(identificationNo))
                .phoneNo(phoneNo)
                .monthlyIncome(monthlyIncome)
                .password(password)
                .build();
    }
}
