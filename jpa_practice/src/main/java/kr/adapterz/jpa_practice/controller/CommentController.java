package kr.adapterz.jpa_practice.controller;

import kr.adapterz.jpa_practice.dto.comment.CommentResponse;
import kr.adapterz.jpa_practice.dto.comment.CreateCommentRequest;
import kr.adapterz.jpa_practice.dto.comment.UpdateCommentRequest;
import kr.adapterz.jpa_practice.entity.Comment;
import kr.adapterz.jpa_practice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentResponse create(@RequestBody CreateCommentRequest request) {
        Comment comment = commentService.create(
                request.getWriterId(),
                request.getPostId(),
                request.getContent()
        );
        return CommentResponse.of(comment);
    }

    @GetMapping
    public List<CommentResponse> getCommentsForPost(@PathVariable("postId") Long postId) {
        List<Comment> comments = commentService.findAllByPostId(postId);
        List<CommentResponse> result = new ArrayList<>();

        for(int i=0; i<comments.size(); i++) {
            Comment comment = comments.get(i);
            CommentResponse dto = CommentResponse.of(comment);
            result.add(dto);
        }
        return result;
    }

    @GetMapping("/{commentId}")
    public CommentResponse get(@PathVariable Long commentId) {
        return CommentResponse.of(commentService.findById(commentId));
    }

    @PatchMapping("/{commentId}")
    public CommentResponse update(@PathVariable Long commentId,
                                  @RequestBody UpdateCommentRequest request) {
        Comment updatedComment = commentService.update(commentId, request.getContent());
        return CommentResponse.of(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }
    //댓글 수정
    //PATCH /api/v1/comments/{commentId}
    @PatchMapping("/api/v1/comments/{commentId}")
    public CommentResponse updateStandalone(@PathVariable Long commentId,
                                            @RequestBody UpdateCommentRequest request) {
        Comment updatedComment = commentService.update(commentId, request.getContent());
        return CommentResponse.of(updatedComment);
    }

    //댓글 삭제
    //DELETE /api/v1/comments/{commentId}
    @DeleteMapping("/api/v1/comments/{commentId}")
    public void deleteStandalone(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }
}
