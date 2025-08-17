package com.taskforge.backend.service;

import com.taskforge.backend.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserRegistrationResponseDto saveUser(UserRequestDto user);
    UserResponseDto findUserById(Long id);
    UserLoginResponseDto loginAUser(UserLoginRequestDto userLoginRequestDto);
    Page<UserResponseDto> findAllUsers(Pageable pageable);
    void deleteUserById(Long id);
    UserResponseDto updateUsernameById(Long id, String name);
}
