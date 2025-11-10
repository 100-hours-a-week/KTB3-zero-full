package kr.adapterz.jpa_practice.controller;

import kr.adapterz.jpa_practice.entity.Post;
import kr.adapterz.jpa_practice.service.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostResponse create(@RequestBody CreatePostRequest request) {
        Post post = postService.create(request.writerId, request.title, request.content);
        return PostResponse.of(post);
    }

    @GetMapping("/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return PostResponse.of(postService.findById(postId));
    }

    @PatchMapping("/{postId}")
    public PostResponse update(@PathVariable Long postId, @RequestBody UpdatePostRequest request) {
        Post updatedPost = postService.update(postId, request.title, request.content);
        return PostResponse.of(updatedPost);
    }

    @DeleteMapping("/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }

    @Data
    public static class UpdatePostRequest {
        private String title;
        private String content;
    }

    @Data
    public static class CreatePostRequest {
        private Long writerId;
        private String title;
        private String content;
    }

    @Data
    public static class PostResponse {
        private Long id;
        private String title;
        private String content;
        private Long writerId;
        private String writerNickname;

        public static PostResponse of(Post post) {
            return new PostResponse(post.getId(), post.getTitle(), post.getContent(),
                    post.getWriter().getId(), post.getWriter().getNickname());
        }

        public PostResponse(Long id, String title, String content, Long writerId, String writerNickname) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.writerId = writerId;
            this.writerNickname = writerNickname;
        }
    }
}
