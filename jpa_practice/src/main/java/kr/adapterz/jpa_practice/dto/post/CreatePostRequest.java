package kr.adapterz.jpa_practice.dto.post;

import lombok.Data;

@Data
public class CreatePostRequest {
    private Long writerId;
    private String title;
    private String content;

    private String country;
    private String theme;
    private String mood;
    private Boolean isAnonymous;

    public CreatePostRequest() {}
}


