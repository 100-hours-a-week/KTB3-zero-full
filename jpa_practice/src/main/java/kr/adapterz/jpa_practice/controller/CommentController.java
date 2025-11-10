package kr.adapterz.jpa_practice.controller;

import kr.adapterz.jpa_practice.entity.Comment;
import kr.adapterz.jpa_practice.service.CommentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentResponse create(@RequestBody CreateCommentRequest request) {
        Comment comment = commentService.create(request.writerId, request.postId, request.content);
        return CommentResponse.of(comment);
    }

    @GetMapping
    public List<CommentResponse> getCommentsForPost(@PathVariable("postId") Long postId) {
        List<Comment> comments = commentService.findAllByPostId(postId);
        List<CommentResponse> responseList = comments.stream().map(CommentResponse::of).collect(Collectors.toList());

        return responseList;
    }

    @GetMapping("/{commentId}")
    public CommentResponse get(@PathVariable Long commentId) {
        return CommentResponse.of(commentService.findById(commentId));
    }

    @PatchMapping("/{commentId}")
    public CommentResponse update(@PathVariable Long commentId, @RequestBody UpdateCommentRequest request) {
        Comment updatedComment = commentService.update(commentId, request.content);
        return CommentResponse.of(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }

    @Data
    public static class UpdateCommentRequest {
        private String content;
    }

    @Data
    public static class CreateCommentRequest {
        private Long writerId;
        private Long postId;
        private String content;
    }

    @Data
    public static class CommentResponse {
        private Long id;
        private String content;
        private Long postId;
        private Long writerId;
        private String writerNickname;

        public static CommentResponse of(Comment comment) {
            return new CommentResponse(comment.getId(), comment.getContent(), comment.getPost().getId(), comment.getWriter().getId(), comment.getWriter().getNickname());
        }

        public CommentResponse(Long id, String content, Long postId, Long writerId, String writerNickname) {
            this.id = id;
            this.content = content;
            this.postId = postId;
            this.writerId = writerId;
            this.writerNickname = writerNickname;
        }
    }
}
