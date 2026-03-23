package com.chessphere.user.controller;

import com.chessphere.user.dto.ApiResponse;
import com.chessphere.user.dto.UserRequestDto;
import com.chessphere.user.service.inter.AuthServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {

    @Autowired
    private AuthServiceInter authService;

    //    Register
    @PostMapping("/auth/register")
    public String registerUser(@RequestBody UserRequestDto userRequestDto) {
        authService.register(userRequestDto);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDto userRequestDto) {
        return authService.login(userRequestDto);
    }
}
