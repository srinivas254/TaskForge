package com.taskforge.backend.controller;

import com.taskforge.backend.dto.*;
import com.taskforge.backend.entity.User;
import com.taskforge.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponseDto> registerUser(@Valid @RequestBody UserRequestDto user){
        UserRegistrationResponseDto savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> loginUser(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto){
        UserLoginResponseDto loginUser = userService.loginAUser(userLoginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(loginUser);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable  Long id){
        UserResponseDto savedUser = userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(savedUser);
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(Pageable pageable){
        Page<UserResponseDto> users = userService.findAllUsers(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> updateNameById(@PathVariable Long id,@RequestParam String name){
        UserResponseDto updatedUser = userService.updateUsernameById(id,name);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

}
