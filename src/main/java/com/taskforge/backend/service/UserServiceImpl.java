package com.taskforge.backend.service;

import com.taskforge.backend.entity.User;
import com.taskforge.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public User saveUser(User user){
        return userRepository.save(user);
    }
    @Override
    public User findUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }
    @Override
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
    @Override
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

}
