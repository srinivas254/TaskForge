package com.taskforge.backend.service;

import com.taskforge.backend.dto.UserRequestDto;
import com.taskforge.backend.dto.UserResponseDto;
import com.taskforge.backend.entity.User;
import com.taskforge.backend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto user){
        User euser = new User();
        modelMapper.map(user,euser);
        User savedUser = userRepository.save(euser);
        return modelMapper.map(savedUser,UserResponseDto.class);
    }

    @Override
    public UserResponseDto findUserById(Long id){
        User user = userRepository.findById(id).orElse(null);
        return modelMapper.map(user,UserResponseDto.class);
    }

    @Override
    public Page<UserResponseDto> findAllUsers(Pageable pageable){
        Page<User> userpage = userRepository.findAll(pageable);
        return userpage.map( user -> modelMapper.map(user, UserResponseDto.class));
    }

    @Override
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseDto updateUsernameById(Long id,String name){
        User euser = userRepository.findById(id).orElse(null);
        euser.setName(name);
        User updatedUser = userRepository.save(euser);
        return modelMapper.map(updatedUser, UserResponseDto.class);
    }

}
