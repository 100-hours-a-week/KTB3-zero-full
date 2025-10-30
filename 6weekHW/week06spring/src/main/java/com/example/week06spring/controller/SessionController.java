package com.example.week06spring.controller;

import com.example.week06spring.dto.LoginRequestDto;
import com.example.week06spring.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;
    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    //Post /api/sessions/login
    @Operation(summary = "로그인", description = "이메일, 비밀번호로 로그인-토큰을 발급 받습니다.")
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginDto) {
        return sessionService.login(loginDto);
    }

    //
    @Operation(summary = "로그아웃", description = "로그아웃-토큰이 삭제됩니다.")
    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String token) {
        sessionService.logout(token);
    }

}
