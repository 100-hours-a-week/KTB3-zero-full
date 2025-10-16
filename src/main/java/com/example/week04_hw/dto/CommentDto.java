package com.example.week04_hw.dto;

public class CommentDto {
    private Long id;
    private Long postId;
    private String author;
    private String content;
    private String authorUserId;

    public CommentDto() {}

    public CommentDto(Long id, Long postId, String author, String content) {
        this.id = id;
        this.postId = postId;
        this.author = author;
        this.content = content;
    }

    public CommentDto(Long id, Long postId, String author, String content, String authorUserId) {
        this(id, postId, author, content);
        this.authorUserId = authorUserId;
    }

    public Long getId() { return id; }
    public Long getPostId() { return postId; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }
    public String getAuthorUserId() { return authorUserId; }

    public void setId(Long id) { this.id = id; }
    public void setPostId(Long postId) { this.postId = postId; }
    public void setAuthor(String author) { this.author = author; }
    public void setContent(String content) { this.content = content; }
    public void setAuthorUserId(String authorUserId) { this.authorUserId = authorUserId; }
}
