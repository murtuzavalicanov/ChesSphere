package com.chessphere.user.controller;

import com.chessphere.user.dto.UserRequestDto;
import com.chessphere.user.dto.UserResponseDto;
import com.chessphere.user.service.inter.UserServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserServiceInter userService;

    @GetMapping("/me")
    public UserResponseDto getCurrentUser(
            @RequestHeader("loggedInUser") String loggedInUser
    ) {
        log.info("UserController.getCurrentUser.loggedInUser={}", loggedInUser);
        return userService.getUserByUsername(loggedInUser);
    }

//    CRUD
    @GetMapping("/users")
    public List<UserResponseDto> getAllUsers() {
        return userService.getUsers();
    }

    @PostMapping("/save")
    public String saveUser(@RequestBody UserRequestDto userRequestDto) {
        userService.saveUser(userRequestDto);
        return "User saved successfully";
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable UUID id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable UUID id, @RequestBody UserRequestDto userRequestDto) {
        userService.updateUser(id, userRequestDto);
        return "User updated successfully";
    }


}
