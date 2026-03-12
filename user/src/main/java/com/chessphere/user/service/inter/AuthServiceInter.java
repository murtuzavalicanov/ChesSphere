package com.chessphere.user.service.inter;

import com.chessphere.user.dto.UserRequestDto;

public interface AuthServiceInter {
    void register(UserRequestDto userRequestDto);

    String login(UserRequestDto userRequestDto);
}
