package com.payten.credit.controller.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Identification number must not be empty")
    @NotNull(message = "Identification number must not be empty")
    private String identificationNo;

    @NotBlank(message = "Password must not be empty")
    @NotNull(message = "Password must not be empty")
    private String password;
}
