package com.taskforge.backend.service;

import com.taskforge.backend.dto.UserRequestDto;
import com.taskforge.backend.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponseDto saveUser(UserRequestDto user);
    UserResponseDto findUserById(Long id);
    Page<UserResponseDto> findAllUsers(Pageable pageable);
    void deleteUserById(Long id);
    UserResponseDto updateUsernameById(Long id, String name);
}
