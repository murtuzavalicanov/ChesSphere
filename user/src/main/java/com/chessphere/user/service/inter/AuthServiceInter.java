package com.chessphere.user.service.inter;

import com.chessphere.user.dto.UserRequestDto;
import org.springframework.http.ResponseEntity;

public interface AuthServiceInter {
    void register(UserRequestDto userRequestDto);

    ResponseEntity<?> login(UserRequestDto userRequestDto);
}
