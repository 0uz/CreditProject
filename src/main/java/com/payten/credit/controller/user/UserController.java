package com.payten.credit.controller.user;

import com.payten.credit.service.user.User;
import com.payten.credit.service.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserCreateResponse create(@RequestBody @Valid UserCreateRequest request){
        Long createdUserId = userService.create(request.toModel());
        return UserCreateResponse.builder().id(createdUserId).build();
    }

    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUser(@PathVariable Long userId){
        User user = userService.retrieve(userId);
        return UserResponse.convertTo(user);
    }


    @PutMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserCreateResponse updateUser(@RequestBody UserCreateRequest request, @PathVariable Long userId){
        Long updatedUser = userService.update(request.toModel(), userId);
        return UserCreateResponse.builder().id(updatedUser).build();
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId){
        userService.delete(userId);
    }


}
