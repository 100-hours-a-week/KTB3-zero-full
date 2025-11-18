package kr.adapterz.jpa_practice.dto.post;

import lombok.Data;

@Data
public class UpdatePostRequest {
    private String title;
    private String content;
}
