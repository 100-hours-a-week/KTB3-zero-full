package com.example.week06spring.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SessionRepository {

    private final Map<String, Long> sessionRepository = new HashMap<>(); //Key: 세션 토큰, value: 사용자 ID

    //토큰 저장
    public void save(String token, Long userId) {
        sessionRepository.put(token, userId);
    }

    //토큰으로 사용자 ID 찾기
    public Long findUserIdByToken(String token) {
        return sessionRepository.get(token);
    }

    //토큰으로 세션 삭제
    public void deleteByToken(String token) {
        sessionRepository.remove(token);
    }

    @PostConstruct
    public void init() {
        // 세션 저장소가 비어있다면,
        if (sessionRepository.isEmpty()) {
            String testToken = "myTestToken123";
            Long testUserId = 0L;
            save(testToken, testUserId);

        }
    }
}
