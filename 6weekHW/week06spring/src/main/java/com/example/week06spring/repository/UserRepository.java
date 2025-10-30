package com.example.week06spring.repository;

import com.example.week06spring.dto.UserDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private final Map<Long, UserDto> userRepository = new LinkedHashMap<>();
    long sequence = 0l;

    public UserDto save(UserDto userDto) {
        userDto.setUserId(sequence++);
        userRepository.put(userDto.getUserId(), userDto);
        return userDto;
    }

    public UserDto findById(Long id) {return userRepository.get(id);}

    //이메일로 유저 찾기
    //[회원 가입] 중복하는 이메일 있는지 확인 위해서(중복 이메일 있으면 그 이메일의 유저 반환, 없으면 null)
    //[로그인] 존재하는 이메일인지 확인
    public UserDto findByUserEmail(String userEmail) {
        List<UserDto> allUsersList = new ArrayList<>(userRepository.values());

        for (int i=0; i<allUsersList.size(); i++ ) {
            UserDto user = allUsersList.get(i);
            if(user.getUserEmail().equals(userEmail)) {
                return user;
            }
        }
        return null;
    }

    //닉네임 찾기 -> 회원 가입시 중복 확인
    public UserDto findByUserNickname(String Nickname) {
        List<UserDto> allUsersList = new ArrayList<>(userRepository.values());

        for (int i=0; i<allUsersList.size(); i++ ) {
            UserDto user = allUsersList.get(i);
            if(user.getNickname().equals(Nickname)) {
                return user;
            }
        }
        return null;
    }

    //회원 삭제
    public void delete(Long id) {userRepository.remove(id);}

    //회원 수정
    public UserDto update(Long id, UserDto userDto) {
        userRepository.replace(id, userDto);
        return userDto;
    }

    @PostConstruct
    public void init() {
        if (userRepository.isEmpty()) {

            // 1번 유저 (ID: 0L)
            UserDto user1 = new UserDto();
            user1.setUserEmail("test@test.com");
            user1.setPassword("password123");
            user1.setNickname("테스트유저1");
            save(user1);

            // 2번 유저 (ID: 1L)
            UserDto user2 = new UserDto();
            user2.setUserEmail("user@user.com");
            user2.setPassword("password456");
            user2.setNickname("일반사용자2");
            save(user2);
        }
    }
}



