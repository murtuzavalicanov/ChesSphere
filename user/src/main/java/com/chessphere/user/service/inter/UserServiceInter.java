package com.chessphere.user.service.inter;

import com.chessphere.user.dto.UserRequestDto;
import com.chessphere.user.dto.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserServiceInter {
    List<UserResponseDto> getUsers();

    void saveUser(UserRequestDto userRequestDto);

    UserResponseDto getUserById(UUID uuid);

    void deleteUser(UUID uuid);

    void updateUser(UUID id, UserRequestDto userRequestDto);
}
