package com.example.week06spring.controller;

import com.example.week06spring.dto.PostDto;
import com.example.week06spring.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) { //생성자로 PostService 주입 받음
        this.postService = postService;
    }

    //GET /posts
    //전체 게시물 조회
    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    //GET /posts/{postId}
    //postId 게시물 조회
    @GetMapping("/{postId}")
    public PostDto getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    //Post /posts/{
    //게시물 작성

    //DELETE /posts/{postId}
    //{postId} 게시물 삭제

    //PUT /posts/{postId}
    //{postId} 게시물 수정


}
