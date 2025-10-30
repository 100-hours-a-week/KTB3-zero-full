package com.example.week06spring.controller;

import com.example.week06spring.dto.UserDto;
import com.example.week06spring.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Post /api/users
    //회원 가입
    @Operation(summary="회원 가입", description = "회원 가입을 합니다.")
    @PostMapping("/signup")
    public UserDto signup(@RequestBody UserDto userDto) {
        return userService.signup(userDto);
    }

    //Put /api/users/{userId}
    //회원 정보 수정
    @Operation(summary="회원 정보 수정", description = "회원 정보를 수정합니다.")
    @PutMapping("/{userId}")
    public UserDto update(
            @PathVariable Long userId,
            @RequestBody UserDto userDto
    ) {
        return userService.updateUser(userId, userDto);
    }

    //Delete /api/users/{userId}
    //회원 탈퇴
    @Operation(summary="회원 탈퇴")
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
