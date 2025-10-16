package com.example.week04_hw.service;

import com.example.week04_hw.dto.CommentDto;
import com.example.week04_hw.repository.CommentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class CommentService {
    private final CommentRepository commentRepo;
    private final PostService postService;

    public CommentService(CommentRepository commentRepo, PostService postService) {
        this.commentRepo = commentRepo;
        this.postService = postService;
    }

    public List<CommentDto> listByPost(Long postId) {
        postService.getPostById(postId);
        return commentRepo.findByPostId(postId);
    }

    public CommentDto get(Long commentId) {
        return commentRepo.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "COMMENT_NOT_FOUND"));
    }

    public CommentDto create(Long postId, CommentDto form) {
        postService.getPostById(postId);
        CommentDto c = new CommentDto(null, postId, form.getAuthor(), form.getContent(), form.getAuthorUserId());
        return commentRepo.save(c);
    }

    public CommentDto update(Long postId, Long commentId, CommentDto form, String currentUserId) {
        postService.getPostById(postId);
        CommentDto cur = get(commentId);
        if (!Objects.equals(cur.getPostId(), postId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "COMMENT_POST_MISMATCH");
        }
        if (!Objects.equals(cur.getAuthorUserId(), currentUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT_OWNER");
        }
        if (form.getContent() != null) cur.setContent(form.getContent());
        return commentRepo.save(cur);
    }

    public void delete(Long postId, Long commentId, String currentUserId) {
        postService.getPostById(postId);
        CommentDto cur = get(commentId);
        if (!Objects.equals(cur.getPostId(), postId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "COMMENT_POST_MISMATCH");
        }
        if (!Objects.equals(cur.getAuthorUserId(), currentUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT_OWNER");
        }
        commentRepo.deleteById(commentId);
    }
}
