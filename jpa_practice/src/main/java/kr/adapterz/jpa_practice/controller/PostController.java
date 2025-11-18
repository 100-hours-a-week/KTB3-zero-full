package kr.adapterz.jpa_practice.controller;

import kr.adapterz.jpa_practice.dto.post.CreatePostRequest;
import kr.adapterz.jpa_practice.dto.post.PostResponse;
import kr.adapterz.jpa_practice.dto.post.UpdatePostRequest;
import kr.adapterz.jpa_practice.entity.Post;
import kr.adapterz.jpa_practice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostResponse create(@RequestBody CreatePostRequest request) {
        Post post = postService.create(
                request.getWriterId(),
                request.getTitle(),
                request.getContent()
        );
        return PostResponse.of(post);
    }

    @GetMapping("/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return PostResponse.of(postService.findById(postId));
    }

    @PatchMapping("/{postId}")
    public PostResponse update(@PathVariable Long postId,
                               @RequestBody UpdatePostRequest request) {
        Post updatedPost = postService.update(postId, request.getTitle(), request.getContent());
        return PostResponse.of(updatedPost);
    }

    @DeleteMapping("/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}
