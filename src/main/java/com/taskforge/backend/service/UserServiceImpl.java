package com.taskforge.backend.service;

import com.taskforge.backend.dto.*;
import com.taskforge.backend.entity.Role;
import com.taskforge.backend.entity.User;
import com.taskforge.backend.exception.InvalidPasswordException;
import com.taskforge.backend.exception.UserNotFoundException;
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
    public UserRegistrationResponseDto saveUser(UserRequestDto user){
        User euser = new User();
        modelMapper.map(user,euser);
        euser.setRole(Role.USER);
        euser.setVerified(false);
        User savedUser = userRepository.save(euser);
        return UserRegistrationResponseDto.builder()
                .message(savedUser.getId() != null ? "User registration successful":"User registration failed")
                .id(savedUser.getId())
                .build();
    }

    @Override
    public UserLoginResponseDto loginAUser(UserLoginRequestDto userLoginRequestDto){
        String emailOrPhone = userLoginRequestDto.getEmailOrPhone();
        String providedPass = userLoginRequestDto.getPassword();
        User userLogin;

        if(emailOrPhone.contains("@")){
           userLogin = userRepository.findByEmail(emailOrPhone)
                   .orElseThrow(()-> new UserNotFoundException("User not found with email "+ emailOrPhone));
        }else if(emailOrPhone.length()==10){
            userLogin = userRepository.findByPhone(emailOrPhone)
                    .orElseThrow(()-> new UserNotFoundException("User not found with phone "+ emailOrPhone));
        }else{
            throw new IllegalArgumentException("Invalid email or phone number");
        }

        if(!userLogin.getPassword().equals(providedPass)){
            throw new InvalidPasswordException("Entered password is incorrect");
        }
        return UserLoginResponseDto.builder()
                .message(userLogin.getId() != null ? "Login successfull":"Login failed")
                .id(userLogin.getId())
                .token(null)
                .name(userLogin.getName())
                .build();
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
