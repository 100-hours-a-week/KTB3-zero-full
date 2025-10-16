package com.example.week04_hw.dto;

public class PostDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private String tags;
    private String authorUserId;

    public PostDto() {}

    public PostDto(Long id, String title, String author, String content, String tags) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.tags = tags;
    }

    public PostDto(Long id, String title, String author, String content, String tags, String authorUserId) {
        this(id, title, author, content, tags);
        this.authorUserId = authorUserId;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }
    public String getTags() { return tags; }
    public String getAuthorUserId() { return authorUserId; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setContent(String content) { this.content = content; }
    public void setTags(String tags) { this.tags = tags; }
    public void setAuthorUserId(String authorUserId) { this.authorUserId = authorUserId; }
}
