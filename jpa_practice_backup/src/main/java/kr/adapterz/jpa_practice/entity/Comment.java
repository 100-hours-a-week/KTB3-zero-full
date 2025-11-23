package kr.adapterz.jpa_practice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id") //comment.user_id -> post.user_id
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") //comment.user_id -> user.user_id
    private User writer;

    protected Comment() {}

    public Comment(String content, Post post, User writer){
        this.content = content;
        this.post = post;
        this.writer = writer;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}
