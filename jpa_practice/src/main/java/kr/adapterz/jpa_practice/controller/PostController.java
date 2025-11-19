package kr.adapterz.jpa_practice.controller;

import kr.adapterz.jpa_practice.dto.post.CreatePostRequest;
import kr.adapterz.jpa_practice.dto.post.PostResponse;
import kr.adapterz.jpa_practice.dto.post.UpdatePostRequest;
import kr.adapterz.jpa_practice.entity.Post;
import kr.adapterz.jpa_practice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //게시글 목록 조회
    @GetMapping
    public List<PostResponse> getAll() {
        List<Post> posts = postService.findAll();
        List<PostResponse> result = new ArrayList<>();

        for (int i=0; i<posts.size(); i++) {
            Post post = posts.get(i);
            PostResponse dto = PostResponse.of(post);
            result.add(dto);
        }
        return result;
    }
    //게시글 작성
    @PostMapping
    public PostResponse create(@RequestBody CreatePostRequest request) {
        Post post = postService.create(
                request.getWriterId(),
                request.getTitle(),
                request.getContent()
        );
        return PostResponse.of(post);
    }

    //특정 게시글 조회
    @GetMapping("/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return PostResponse.of(postService.findById(postId));
    }

    //게시글 업데이트
    @PatchMapping("/{postId}")
    public PostResponse update(@PathVariable Long postId,
                               @RequestBody UpdatePostRequest request) {
        Post updatedPost = postService.update(postId, request.getTitle(), request.getContent());
        return PostResponse.of(updatedPost);
    }

    //게시글 삭제
    @DeleteMapping("/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}
