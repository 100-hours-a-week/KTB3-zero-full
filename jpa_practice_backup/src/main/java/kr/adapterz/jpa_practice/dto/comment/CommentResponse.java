package kr.adapterz.jpa_practice.dto.comment;

import kr.adapterz.jpa_practice.entity.Comment;
import lombok.Data;

@Data
public class CommentResponse {
    private Long id;
    private String content;
    private Long postId;
    private Long writerId;
    private String writerNickname;

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getPost().getId(),
                comment.getWriter().getId(),
                comment.getWriter().getNickname()
        );
    }

    public CommentResponse(Long id, String content, Long postId, Long writerId, String writerNickname) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
    }
}
