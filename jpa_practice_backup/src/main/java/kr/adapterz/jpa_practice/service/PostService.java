package kr.adapterz.jpa_practice.service;

import kr.adapterz.jpa_practice.entity.Post;
import kr.adapterz.jpa_practice.entity.User;
import kr.adapterz.jpa_practice.repository.PostRepository;
import kr.adapterz.jpa_practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Post create (Long writerId, String title, String content) {
        User writer = userRepository.findById(writerId).orElseThrow(() -> new IllegalArgumentException("user not found"));
        Post post = new Post(title, content, writer);
        return postRepository.save(post);
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("post not found"));
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByIdDesc();
    }

    @Transactional
    public Post update(Long id, String title, String content) {
        Post post = findById(id);
        if (title != null) post.changeTitle(title);
        if (content != null) post.changeContent(content);
        return post;
    }

    @Transactional
    public void delete(Long id) {
        postRepository.delete(findById(id));
    }
}
