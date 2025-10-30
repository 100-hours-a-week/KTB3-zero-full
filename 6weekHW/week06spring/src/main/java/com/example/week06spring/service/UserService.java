package com.example.week06spring.service;

import com.example.week06spring.dto.PostDto;
import com.example.week06spring.dto.UserDto;
import com.example.week06spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원 가입 - 이메일, 닉네임 중복 확인
    public UserDto signup(UserDto userDto) {
        if (userRepository.findByUserEmail(userDto.getUserEmail()) != null) {
            //이메일 중복
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다.");
        }
        if (userRepository.findByUserNickname(userDto.getNickname()) != null) {
            //닉네임 중복
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용중인 닉네임입니다.");
        }

        //이메일, 비밀번호, 닉네임 패스
        UserDto dto = userRepository.save(userDto);
        return dto;
    }

    //사용자 조회
    public UserDto getUserById(Long id) {
        UserDto dto = userRepository.findById(id);
        if(dto==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자"); // 404 존재하지 않는 사용자 조회 실패
        } else {
            return dto; //200 존재하는 사용자 조회 성공
        }
    }

    //회원 정보 - 비밀번호 수정
    public UserDto updateUser(Long id, UserDto userDto) {
        UserDto existingUser = getUserById(id); //사용자 존재 확인

        existingUser.setPassword(userDto.getPassword());

        UserDto updatedDto = userRepository.update(id, existingUser);
        return updatedDto;
    }

    //회원 탈퇴
    public void deleteUser(Long id) { //게시글 삭제
        getUserById(id);
        userRepository.delete(id);
    }

        }