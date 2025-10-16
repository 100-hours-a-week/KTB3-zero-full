package com.example.week04_hw.repository;

import com.example.week04_hw.dto.UserDto;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.*;

@Repository
public class UserRepository {

    private final Map<Long, UserDto> store = new LinkedHashMap<>();
    private long sequence = 0L;

    @PostConstruct
    public void init() {
        if (store.isEmpty()) {
            UserDto u1 = new UserDto(null, "zero", "password", "제로");
            u1.setUserId(UUID.randomUUID().toString()); // 세션용 userid
            save(u1);

            UserDto u2 = new UserDto(null, "amy", "password", "Amy");
            u2.setUserId(UUID.randomUUID().toString()); // 세션용 userid
            save(u2);
        }
    }

    public UserDto save(UserDto user) {
        if (user.getId() == null) {
            sequence++;
            user.setId(sequence);
        } else if (user.getId() > sequence) {
            sequence = user.getId();
        }
        store.put(user.getId(), user);
        return user;
    }

    public Optional<UserDto> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<UserDto> findByUsername(String username) {
        return store.values().stream()
                .filter(u -> Objects.equals(u.getUsername(), username))
                .findFirst();
    }

    // 세션 userid로 사용자 찾기
    public Optional<UserDto> findByUserId(String userId) {
        return store.values().stream()
                .filter(u -> Objects.equals(u.getUserId(), userId))
                .findFirst();
    }

    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    public List<UserDto> findAll() {
        return new ArrayList<>(store.values());
    }
}
