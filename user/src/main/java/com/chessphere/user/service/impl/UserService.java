package com.chessphere.user.service.impl;

import com.chessphere.user.dto.UserRequestDto;
import com.chessphere.user.dto.UserResponseDto;
import com.chessphere.user.entity.UserEntity;
import com.chessphere.user.exception.UserNotFoundException;
import com.chessphere.user.repository.UserRepo;
import com.chessphere.user.service.inter.UserServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserService implements UserServiceInter {
    UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final UserRepo userRepo;

    @Override
    public List<UserResponseDto> getUsers() {
        log.info("ActionLog.getUsers().started");
        List<UserEntity> users = userRepo.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (UserEntity user : users) {
            UserResponseDto userResponseDto = UserResponseDto.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .build();
            userResponseDtos.add(userResponseDto);
        }
        log.info("ActionLog.getUsers().ended : {}", userResponseDtos);
        return userResponseDtos;
    }

    @Override
    public void saveUser(UserRequestDto userRequestDto) {
        log.info("ActionLog.saveUser().started :  {}", userRequestDto);
        UserEntity userEntity = UserEntity.builder()
                .username(userRequestDto.getUsername())
                .email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .build();
        userRepo.save(userEntity);
        log.info("ActionLog.saveUser().ended : {}", userEntity);
    }

    @Override
    public UserResponseDto getUserById(UUID uuid) {
        log.info("ActionLog.getUserById().started :  {}", uuid);
        UserEntity userEntity = userRepo.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        UserResponseDto userResponseDto= UserResponseDto.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .build();
        log.info("ActionLog.getUserById().ended : {}", userResponseDto);
        return userResponseDto;
    }

    @Override
    public void deleteUser(UUID uuid) {
        log.info("ActionLog.deleteUser().started :  {}", uuid);
        userRepo.deleteById(uuid);
        log.info("ActionLog.deleteUser().ended : {}", uuid);
    }

    @Override
    public void updateUser(UUID id, UserRequestDto userRequestDto) {
        log.info("ActionLog.updateUser().started :  id-{}, userDto-{}", id, userRequestDto);
        UserEntity userDb = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        userDb.setUsername(userRequestDto.getUsername());
        userDb.setEmail(userRequestDto.getEmail());
        userRepo.save(userDb);
        log.info("ActionLog.updateUser().ended : {}", userDb);
    }

    @Override
    public UserResponseDto getUserByUsername(String loggedInUser) {
        log.info("ActionLog.getUserByUsername().started :  {}", loggedInUser);
        Optional<UserEntity> user = userRepo.findByUsername(loggedInUser);
        UserResponseDto userResponseDto= UserResponseDto.builder()
                .username(user.get().getUsername())
                .email(user.get().getEmail())
                .build();
        log.info("ActionLog.getUserByUsername().ended : {}", userResponseDto);
        return userResponseDto;
    }
}
