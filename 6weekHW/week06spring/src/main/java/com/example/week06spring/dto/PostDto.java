package com.example.week06spring.dto;

import org.springframework.stereotype.Component;

@Component
public class PostDto {
    private Long postId;
    private String title;
    private String writer;
    private String content;
    private Long writerId;

    public PostDto() {}
    public PostDto(Long postId, String title, String writer, String content) {
        this.postId=postId;
        this.title=title;
        this.writer=writer;
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
    public String getWriter() { //author getter
        return writer;
    }
    public void setWriter(String writer) { //author setter
        this.writer=writer;
    }
    public String getContent() { //content getter
        return content;
    }
    public void setContent(String content) { //content setter
        this.content=content;
    }

}
