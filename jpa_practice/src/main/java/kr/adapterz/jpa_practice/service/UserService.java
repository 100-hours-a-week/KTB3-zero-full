package kr.adapterz.jpa_practice.service;

import kr.adapterz.jpa_practice.entity.User;
import kr.adapterz.jpa_practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User create(String email, String password, String nickname) {
        User user = new User(email, password, nickname);
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found"));
    }

    public User getReferenceById(Long id) {
        return userRepository.getReferenceById(id);
    }

    @Transactional
    public User update(Long id, String nickname) {
        User user = findById(id);
        if (nickname != null) {
            user.changeNickname(nickname);
        }
        return user;
    }

    @Transactional
    public void delete(Long id) {
        userRepository.delete(findById(id));
    }

}