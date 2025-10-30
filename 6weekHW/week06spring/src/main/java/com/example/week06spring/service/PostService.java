package com.example.week06spring.service;

import com.example.week06spring.dto.PostDto;
import com.example.week06spring.dto.UserDto;
import com.example.week06spring.repository.PostRepository;
import com.example.week06spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) { //PostRepository 주입 받음
        this.postRepository=postRepository;
        this.userRepository=userRepository;
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll();
    } //모든 게시글 조회

    public PostDto getPostById(Long id) { //특정 게시글 조회
        PostDto dto = postRepository.findById(id);
        if(dto==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시물"); // 404 존재하지 않는 게시물 조회 실패
        } else {
            return dto; //200 존재하는 게시물 조회 성공
        }
    }

    public PostDto createPost(PostDto postDto) {//게시글 생성
        PostDto post = postRepository.save(postDto);
        return post;
    }

    public void deletePost(Long id) { //게시글 삭제
        getPostById(id); //게시글 존재 확인
        postRepository.delete(id);
    }

    public PostDto updatePost(Long id, PostDto postDto) {
        PostDto existingPost = getPostById(id); //기존 게시글 존재 확인 + 불러오기

        existingPost.setTitle(postDto.getTitle());
        existingPost.setWriter(postDto.getWriter());
        existingPost.setContent(postDto.getContent());

        PostDto updatedDto = postRepository.update(id, existingPost);
        return updatedDto;
    }

}



