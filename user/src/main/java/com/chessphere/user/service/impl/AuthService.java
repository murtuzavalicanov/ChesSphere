package com.chessphere.user.service.impl;

import com.chessphere.user.util.JwtUtil;
import com.chessphere.user.dto.UserRequestDto;
import com.chessphere.user.entity.UserEntity;
import com.chessphere.user.repository.UserRepo;
import com.chessphere.user.service.inter.AuthServiceInter;
import com.chessphere.user.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthServiceInter {

    AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil; // Token yaradan köməkçi klass

    public String login(UserRequestDto request) {
        UserEntity user =userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Istifadəçi tapımadı!") );
        // Şifrəni yoxla
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // Əgər düzdürsə, Token yarat və qaytar
            return jwtUtil.generateToken(user.getEmail(), user.getRole());
        } else {
            throw new RuntimeException("Yanlış şifrə!");
        }
    }

    @Override
    public void register(UserRequestDto userRequestDto) {
        UserEntity userEntity=new UserEntity();
        userEntity.setUsername(userRequestDto.getUsername());
        userEntity.setEmail(userRequestDto.getEmail());
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setRole(Role.USER);
        userRepo.save(userEntity);
    }
}
