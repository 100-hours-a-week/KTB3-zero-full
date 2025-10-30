package com.example.week06spring.controller;

import com.example.week06spring.dto.PostDto;
import com.example.week06spring.service.PostService;
import com.example.week06spring.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final SessionService sessionService;

    @Autowired
    public PostController(PostService postService, SessionService sessionService)
    { //생성자로 PostService 주입 받음
        this.postService = postService;
        this.sessionService = sessionService;
    }

    //전체 게시물 조회
    //GET /api/posts
    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    //postId 게시물 조회
    //GET /api/posts/{postId}
    @Operation(summary = "게시글 조회", description = "ID를 이용하여 특정 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없음")
    })
    @GetMapping("/{postId}")
    public PostDto getPostById(
            @Parameter(description = "조회할 게시글의 ID", required = true, example = "1")
            @PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    //POST /api/posts
    //새 게시글 작성
    @Operation(summary = "새 게시글 작성", description = "새로운 게시글을 작성합니다.")
    @PostMapping
    public PostDto createPost(@RequestBody PostDto postDto) {
        return postService.createPost(postDto);
    }


    //DELETE /api/posts/{postId}
    //{postId} 게시글 삭제
    @Operation(summary = "게시글 삭제", description = "ID의 게시글을 삭제합니다.")
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        postService.getAllPosts();
    }

    //PUT /api/posts/{postId}
    // {postId} 게시글 수정
    @Operation(summary = "게시글 수정", description = "ID의 게시글을 수정합니다.")
    @PutMapping("/{postId}") //
    public PostDto updatePost(
            @PathVariable Long postId,        // URL 경로에서 postId 받기
            @RequestBody PostDto postDto     // 요청 본문에서 수정할 내용 받기
    ) {
        return postService.updatePost(postId, postDto);
    }
}
