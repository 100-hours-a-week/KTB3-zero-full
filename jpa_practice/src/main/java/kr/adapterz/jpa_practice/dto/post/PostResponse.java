package kr.adapterz.jpa_practice.dto.post;

import kr.adapterz.jpa_practice.entity.Post;
import lombok.Data;

@Data
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Long writerId;
    private String writerNickname;

    public static PostResponse of(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getWriter().getId(),
                post.getWriter().getNickname()
        );
    }

    public PostResponse(Long id, String title, String content, Long writerId, String writerNickname) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
    }
}
