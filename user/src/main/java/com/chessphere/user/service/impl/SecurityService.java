package com.chessphere.user.service.impl;

import com.chessphere.user.entity.UserEntity;
import com.chessphere.user.exception.UserNotFoundException;
import com.chessphere.user.repository.UserRepo;
import com.chessphere.user.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {
    private final UserRepo usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        UserEntity user = usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("username not found"));
        return new User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserEntity userEntity) {
        return userEntity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role)) // Hər bir string rolunu Authority-yə çeviririk
                .collect(Collectors.toList());
    }
}
