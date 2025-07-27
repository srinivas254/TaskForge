package com.taskforge.backend.service;

import com.taskforge.backend.dto.UserRequestDto;
import com.taskforge.backend.dto.UserResponseDto;
import com.taskforge.backend.entity.User;
import java.util.List;

public interface UserService {
    UserResponseDto saveUser(UserRequestDto user);
    UserResponseDto findUserById(Long id);
    List<UserResponseDto> findAllUsers();
    void deleteUserById(Long id);
    UserResponseDto updateUsernameById(Long id, String name);
}
