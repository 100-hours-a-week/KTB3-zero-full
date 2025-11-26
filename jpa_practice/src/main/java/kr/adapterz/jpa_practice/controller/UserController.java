package kr.adapterz.jpa_practice.controller;

import kr.adapterz.jpa_practice.dto.user.CreateUserRequest;
import kr.adapterz.jpa_practice.dto.user.UpdateUserRequest;
import kr.adapterz.jpa_practice.dto.user.UserResponse;
import kr.adapterz.jpa_practice.entity.user.User;
import kr.adapterz.jpa_practice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserResponse create(@RequestBody CreateUserRequest request) {
        User saved = userService.create(
                request.getEmail(),
                request.getPassword(),
                request.getNickname()
        );
        return UserResponse.of(saved);
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return UserResponse.of(userService.findById(id));
    }

    @PatchMapping("/{id}")
    public UserResponse update(@PathVariable Long id,
                               @RequestBody UpdateUserRequest request) {
        User updatedUser = userService.update(id, request.getNickname());
        return UserResponse.of(updatedUser);
    }

    @PatchMapping("/{id}") // 유저 삭제 x
    public void delete(@PathVariable Long id) {

    }
}
