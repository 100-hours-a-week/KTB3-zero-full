package com.example.week04_hw.controller;

import com.example.week04_hw.dto.UserDto;
import com.example.week04_hw.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class AuthController {

    private final UserService users;
    public AuthController(UserService users) { this.users = users; }

    /* =========================
     * HTML Form (Thymeleaf)
     * ========================= */
    @GetMapping("/login")
    public String loginForm(@RequestParam(value="error", required=false) String error, Model model) {
        if (error != null) model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
        return "auth/login";
    }

    @PostMapping(value="/login", consumes="application/x-www-form-urlencoded")
    public String loginFormSubmit(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam(required=false) String next,
                                  HttpSession session, Model model) {
        var u = users.authenticate(username, password);
        if (u.isEmpty()) { model.addAttribute("error","아이디 또는 비밀번호가 올바르지 않습니다."); return "auth/login"; }
        users.login(session, u.get());
        return (next!=null && !next.isBlank()) ? "redirect:"+next : "redirect:/posts";
    }

    @GetMapping("/signup")
    public String signupForm(@RequestParam(value="error", required=false) String error, Model model) {
        if (error != null) model.addAttribute("error", error);
        return "auth/signup";
    }

    @PostMapping(value="/signup", consumes="application/x-www-form-urlencoded")
    public String signupFormSubmit(@RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam String displayName,
                                   Model model) {
        try { users.register(username, password, displayName); }
        catch (RuntimeException e) { model.addAttribute("error", e.getMessage()); return "auth/signup"; }
        return "redirect:/users/login";
    }

    @PostMapping("/logout")
    public String logoutForm(HttpSession session) {
        users.logout(session);
        return "redirect:/posts";
    }

    /** 회원가입(JSON) : 201 / 400(검증실패) / 409(중복) */
    @PostMapping(value="/signup", consumes="application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity<?> signupJson(@RequestBody SignupRequest req) {
        try {
            UserDto u = users.register(req.username, req.password, req.displayName);
            UserDto res = new UserDto(u.getId(), u.getUserId(), u.getUsername(), null, u.getDisplayName());
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (RuntimeException e) {
            String msg = e.getMessage()==null? "Bad request" : e.getMessage();
            HttpStatus code = (msg.contains("이미 사용 중") || msg.contains("중복"))
                    ? HttpStatus.CONFLICT : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(code).body(java.util.Map.of("error", code.name(), "message", msg));
        }
    }

    /** 로그인(JSON) : 200 / 401 */
    @PostMapping(value="/login", consumes="application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity<?> loginJson(@RequestBody LoginRequest req, HttpSession session) {
        var u = users.authenticate(req.username, req.password);
        if (u.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(java.util.Map.of("error","INVALID_CREDENTIALS"));
        }
        users.login(session, u.get());
        return ResponseEntity.ok(java.util.Map.of("message","ok","userid",u.get().getUserId()));
    }

    /** 로그아웃(JSON) : 200(로그인 상태) / 401(비로그인) */
    @PostMapping(value="/logout", produces="application/json")
    @ResponseBody
    public ResponseEntity<?> logoutJson(HttpServletRequest request) {
        HttpSession session = request.getSession(false); //
        if (session == null || session.getAttribute(UserService.SESSION_USERID) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(java.util.Map.of("error","UNAUTHORIZED","message","login required"));
        }
        users.logout(session);
        return ResponseEntity.ok(java.util.Map.of("message","logged_out"));
    }


    public static class SignupRequest { public String username; public String password; public String displayName; }
    public static class LoginRequest { public String username; public String password; }
}
