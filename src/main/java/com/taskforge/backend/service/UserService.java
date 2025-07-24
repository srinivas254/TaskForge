package com.taskforge.backend.service;

import com.taskforge.backend.entity.User;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    User findUserById(Long id);
    List<User> findAllUsers();
    void deleteUserById(Long id);
}
