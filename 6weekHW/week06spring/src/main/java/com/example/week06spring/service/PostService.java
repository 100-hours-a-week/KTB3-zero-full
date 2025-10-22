package com.example.week06spring.service;

import com.example.week06spring.dto.PostDto;
import com.example.week06spring.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) { //PostRepository 주입 받음
        this.postRepository=postRepository;
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll();
    }
    public PostDto getPostById(Long id) {
        PostDto dto = postRepository.findById(id);
        if(dto==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시물"); // 404 존재하지 않는 게시물 조회 실패
        } else {
            return dto; //200 존재하는 게시물 조회 성공
        }
    }

}

