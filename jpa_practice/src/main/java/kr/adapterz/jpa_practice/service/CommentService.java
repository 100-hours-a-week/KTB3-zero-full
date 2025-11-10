package kr.adapterz.jpa_practice.service;

import kr.adapterz.jpa_practice.entity.Comment;
import kr.adapterz.jpa_practice.entity.Post;
import kr.adapterz.jpa_practice.entity.User;
import kr.adapterz.jpa_practice.repository.CommentRepository;
import kr.adapterz.jpa_practice.repository.PostRepository;
import kr.adapterz.jpa_practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment create (Long writerId, Long postId, String content) {
        User writer = userRepository.findById(writerId).orElseThrow(() ->new IllegalArgumentException("user not found"));
        Post post = postRepository.findById(postId).orElseThrow(() ->new IllegalArgumentException("post not found"));
        Comment comment = new Comment(content, post, writer);
        return commentRepository.save(comment);
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("comment not found"));
    }

    public List<Comment> findAllByPostId(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return comments;
    }

    @Transactional
    public Comment update(Long id, String content) {
        Comment comment = findById(id);
        if (content != null) comment.changeContent(content);
        return comment;
    }

    @Transactional
    public void delete(Long id) {
        commentRepository.delete(findById(id));
    }
}
