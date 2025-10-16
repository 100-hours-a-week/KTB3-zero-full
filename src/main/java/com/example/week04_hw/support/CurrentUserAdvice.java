package com.example.week04_hw.support;

import com.example.week04_hw.dto.UserDto;
import com.example.week04_hw.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@ControllerAdvice
public class CurrentUserAdvice {

    private final UserService users;

    public CurrentUserAdvice(UserService users) {
        this.users = users;
    }

    @ModelAttribute("currentUser")
    public UserDto currentUser(HttpSession session) {
        Optional<UserDto> u = users.currentUserBySession(session);
        return u.orElse(null);
    }
}
