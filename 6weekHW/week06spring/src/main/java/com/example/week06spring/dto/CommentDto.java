package com.example.week06spring.dto;

import org.springframework.stereotype.Component;

@Component
public class CommentDto {
    private Long commentId;
    private Long postId;
    private String writer;
    private String content;

    public CommentDto() {}
    public CommentDto(Long commentId, Long postId, String writer, String content) {
        this.commentId = commentId;
        this.postId = postId;
        this.writer = writer;
        this.content = content;
    }

    public Long getCommentId() { return commentId; }
    public void setCommentId(Long commentId) { this.commentId = commentId; }
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }
    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

}
