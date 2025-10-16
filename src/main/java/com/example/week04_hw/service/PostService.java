package com.example.week04_hw.service;

import com.example.week04_hw.dto.PostDto;
import com.example.week04_hw.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class PostService {
    private final PostRepository repo;

    public PostService(PostRepository repo) {
        this.repo = repo;
    }

    public List<PostDto> getAllPosts() {
        var list = repo.findAll();
        list.sort(Comparator.comparingLong(PostDto::getId));
        return list;
    }

    public PostDto getPostById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "POST_NOT_FOUND"));
    }

    public PostDto create(PostDto dto) {
        return repo.save(dto);
    }

    public PostDto update(Long id, PostDto form, String currentUserId) {
        PostDto cur = getPostById(id);
        if (!Objects.equals(cur.getAuthorUserId(), currentUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT_OWNER");
        }
        if (form.getTitle() != null)   cur.setTitle(form.getTitle());
        if (form.getContent() != null) cur.setContent(form.getContent());
        if (form.getTags() != null)    cur.setTags(form.getTags());
        return repo.save(cur);
    }

    public void delete(Long id, String currentUserId) {
        PostDto cur = getPostById(id);
        if (!Objects.equals(cur.getAuthorUserId(), currentUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT_OWNER");
        }
        repo.deleteById(id);
    }
}
