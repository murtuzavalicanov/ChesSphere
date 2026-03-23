package com.chessphere.user.service.impl;

import com.chessphere.user.dto.JwtResponse;
import com.chessphere.user.exception.AuthenticationException;
import com.chessphere.user.util.JwtUtil;
import com.chessphere.user.dto.UserRequestDto;
import com.chessphere.user.entity.UserEntity;
import com.chessphere.user.repository.UserRepo;
import com.chessphere.user.service.inter.AuthServiceInter;
import com.chessphere.user.util.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService implements AuthServiceInter {

    AuthService(UserRepo userRepo, AuthenticationManager authenticationManager, SecurityService userDetailService) {
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final SecurityService userDetailService;

    @Autowired
    private JwtUtil jwtUtil; // Token yaradan köməkçi klass

    public ResponseEntity<?> login(UserRequestDto userRequestDto) {
        log.info("ActionLog.createAuthenticationToken.started : authenticationRequest {}", userRequestDto);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequestDto.getUsername(), userRequestDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("incorrect username or password");
        }
        final UserDetails userDetails = userDetailService.loadUserByUsername(userRequestDto.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);
        JwtResponse jwtResponse = new JwtResponse(jwt);
        log.info("ActionLog.createAuthenticationToken.end : authenticationRequest {}", userRequestDto);
        return ResponseEntity.ok(jwtResponse);
    }

    @Override
    public void register(UserRequestDto userRequestDto) {
        UserEntity userEntity=new UserEntity();
        userEntity.setUsername(userRequestDto.getUsername());
        userEntity.setEmail(userRequestDto.getEmail());
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        userEntity.setPassword(password);
        userEntity.setRole(Role.USER);
        userRepo.save(userEntity);
    }
}
