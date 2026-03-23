package com.chessphere.user.service.impl;

import com.chessphere.user.dto.UserRequestDto;
import com.chessphere.user.dto.UserResponseDto;
import com.chessphere.user.entity.UserEntity;
import com.chessphere.user.exception.UserNotFoundException;
import com.chessphere.user.repository.UserRepo;
import com.chessphere.user.service.inter.UserServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserServiceInter {
    UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final UserRepo userRepo;

    @Override
    public List<UserResponseDto> getUsers() {
        List<UserEntity> users= userRepo.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for(UserEntity user:users){
            UserResponseDto userResponseDto=UserResponseDto.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .build();
            userResponseDtos.add(userResponseDto);
        }
        return userResponseDtos;
    }

    @Override
    public void saveUser(UserRequestDto userRequestDto) {
        UserEntity userEntity = UserEntity.builder()
                .username(userRequestDto.getUsername())
                .email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .build();
        userRepo.save(userEntity);
    }

    @Override
    public UserResponseDto getUserById(UUID uuid) {
        UserEntity userEntity = userRepo.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserResponseDto.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .build();
    }

    @Override
    public void deleteUser(UUID uuid) {
        userRepo.deleteById(uuid);
    }

    @Override
    public void updateUser(UUID id, UserRequestDto userRequestDto) {
        UserEntity userDb = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        userDb.setUsername(userRequestDto.getUsername());
        userDb.setEmail(userRequestDto.getEmail());
        userRepo.save(userDb);
    }


}
