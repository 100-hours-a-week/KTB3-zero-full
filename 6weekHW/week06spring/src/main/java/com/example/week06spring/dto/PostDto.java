package com.example.week06spring.dto;

import org.springframework.stereotype.Component;

@Component
public class PostDto {
    Long postId;
    String title;
    String author;
    String content;

    public PostDto() {}
    public PostDto(Long postId, String title, String author, String content) {
        this.postId=postId;
        this.title=title;
        this.author=author;
        this.content=content;
    }

    public Long getPostId() { //postId getter
        return postId;
    }
    public void setPostId(Long postId) { //postId setter
        this.postId=postId;
    }
    public String getTitle() { //title getter
        return title;
    }
    public void setTitle(String title) { //title setter
        this.title=title;
    }
    public String getAuthor() { //author getter
        return author;
    }
    public void setAuthor(String author) { //author setter
        this.author=author;
    }
    public String getContent() { //content getter
        return content;
    }
    public void setContent(String content) { //content setter
        this.content=content;
    }
}
