package com.example.week06spring.service;

import com.example.week06spring.dto.LoginRequestDto;
import com.example.week06spring.dto.UserDto;
import com.example.week06spring.repository.SessionRepository;
import com.example.week06spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    @Autowired
    public SessionService(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    //로그인
    public String login(LoginRequestDto loginDto) {
        UserDto user = userRepository.findByUserEmail(loginDto.getUserEmail());
        if(user==null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 이메일입니다.");
        } else {
            if(!user.getPassword().equals(loginDto.getUserPassword())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
            } else {
                //이메일, 비밀번호 일치 -토큰 생성
                String token = UUID.randomUUID().toString();
                //토큰, 사용자 ID 저장
                sessionRepository.save(token, user.getUserId());
                return token;
            }
        }
    }

    //로그아웃
    public void logout(String token) {
        sessionRepository.deleteByToken(token);
    }

    //토큰으로 사용자 ID 가져오기 - 인증
    public Long getUserIdByToken(String token) {
        Long userId = sessionRepository.findUserIdByToken(token);

        if(userId==null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");
        }
        return userId;
    }



}
