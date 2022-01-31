package com.payten.credit.controller.user;

import com.payten.credit.service.user.User;
import com.payten.credit.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
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

    //TODO Update and delete

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId){
        userService.delete(userId);
    }


}
