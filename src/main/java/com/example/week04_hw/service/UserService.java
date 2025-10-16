package com.example.week04_hw.service;

import com.example.week04_hw.common.BadRequestException;
import com.example.week04_hw.common.ConflictException;
import com.example.week04_hw.dto.UserDto;
import com.example.week04_hw.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    public static final String SESSION_KEY = "loginUser";
    public static final String SESSION_USERID = "userid";

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UserDto register(String username, String passwordPlain, String displayName) {
        if (username == null || username.isBlank())
            throw new BadRequestException("username은 필수입니다.");
        if (passwordPlain == null || passwordPlain.isBlank())
            throw new BadRequestException("password는 필수입니다.");
        if (displayName == null || displayName.isBlank())
            throw new BadRequestException("displayName은 필수입니다.");
        if (username.length() < 3)
            throw new BadRequestException("username은 최소 3자 이상이어야 합니다.");
        if (passwordPlain.length() < 4)
            throw new BadRequestException("password는 최소 4자 이상이어야 합니다.");

        if (repo.existsByUsername(username))
            throw new ConflictException("이미 사용 중인 username 입니다.");

        UserDto user = new UserDto(null, username, passwordPlain, displayName);
        user.setUserId(UUID.randomUUID().toString());
        return repo.save(user);
    }

    public Optional<UserDto> authenticate(String username, String passwordPlain) {
        return repo.findByUsername(username)
                .filter(u -> u.getPasswordHash().equals(passwordPlain));
    }

    public void login(HttpSession session, UserDto user) {
        session.setAttribute(SESSION_USERID, user.getUserId());
    }

    public Optional<UserDto> currentUserBySession(HttpSession session) {
        Object raw = session.getAttribute(SESSION_USERID);
        if (raw instanceof String uid) {
            return repo.findByUserId(uid);
        }
        return Optional.empty();
    }

    public void logout(HttpSession session) { session.invalidate(); }
}
