package kr.adapterz.jpa_practice.service;

import kr.adapterz.jpa_practice.dto.auth.LoginRequest;
import kr.adapterz.jpa_practice.dto.user.CreateUserRequest;
import kr.adapterz.jpa_practice.entity.user.UserRole;
import kr.adapterz.jpa_practice.repository.UserRepository;
import kr.adapterz.jpa_practice.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import kr.adapterz.jpa_practice.entity.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public User signup(CreateUserRequest request) {
        //이메일 중복 체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), encodedPassword, request.getNickname(), UserRole.USER);
        return userRepository.save(user);
    }

    @Transactional
    public User login(LoginRequest request) {
        //미인증 객체 생성
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        //AuthenticationManager에 넘겨서 객체 인증
        Authentication authentication = authenticationManager.authenticate(authToken);

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();


    }
}