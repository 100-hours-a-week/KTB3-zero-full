package com.example.week06spring.controller;

import com.example.week06spring.dto.CommentDto;
import com.example.week06spring.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "특정 게시글의 모든 댓글 조회", description = "postId에 해당하는 게시글의 모든 댓글을 조회합니다.")
    // GET /api/posts/{postId}/comments
    @GetMapping
    public List<CommentDto> getCommentsForPost(@PathVariable Long postId) {
        // URL의 {postId}가 이 변수로 들어옵니다.
        return commentService.getCommentsByPostId(postId);
    }

    @Operation(summary = "특정 게시글에 댓글 작성", description = "postId에 해당하는 게시글에 새 댓글을 작성합니다.")
    // POST /api/posts/{postId}/comments
    @PostMapping
    public CommentDto createComment(
            @PathVariable Long postId, // URL의 {postId}
            @RequestBody CommentDto commentDto // 요청 본문의 댓글 정보 (작성자, 내용)
    ) {
        // 서비스 레이어에 postId와 댓글 내용을 모두 전달합니다.
        return commentService.createComment(postId, commentDto);
    }

    // 댓글 삭제
    // DELETE /api/posts/{postId}/comments/{commentId}
    @Operation(summary = "댓글 삭제", description = "commentId에 해당하는 댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}")
    public void deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId  // URL의 {commentId} (삭제할 대상)
    ) {
        commentService.deleteComment(commentId);
    }

    // 댓글 수정
    // PUT /api/posts/{postId}/comments/{commentId}
    @Operation(summary = "댓글 수정", description = "commentId에 해당하는 댓글을 수정합니다.")
    @PutMapping("/{commentId}")
    public CommentDto updateComment(
            @PathVariable Long postId,    // URL의 {postId} (검증용)
            @PathVariable Long commentId, // URL의 {commentId} (수정할 대상)
            @RequestBody CommentDto commentDto // 수정할 내용 (작성자, 내용)
    ) {
        // (참고) commentService.updateComment 내부에서 postId 검증 로직 추가 가능
        return commentService.updateComment(commentId, commentDto);
    }
}
