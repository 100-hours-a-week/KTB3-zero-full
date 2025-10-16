package com.example.week04_hw.repository;

import com.example.week04_hw.dto.CommentDto;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CommentRepository {
    private final Map<Long, CommentDto> store = new LinkedHashMap<>();
    private long sequence = 0L;

    @PostConstruct
    public void init() {
        if (store.isEmpty()) {
            // 예시 시드: 게시글 1,2에 댓글 몇 개
            save(new CommentDto(null, 1L, "Amy", "출발 좋다!"));
            save(new CommentDto(null, 1L, "해리", "데이터 모델 공유해줘~"));
            save(new CommentDto(null, 2L, "제로", "레전드 각"));
        }
    }

    public CommentDto save(CommentDto c) {
        if (c.getId() == null) {
            sequence++;
            c.setId(sequence);
        } else if (c.getId() > sequence) {
            sequence = c.getId();
        }
        store.put(c.getId(), c);
        return c;
    }

    public Optional<CommentDto> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<CommentDto> findByPostId(Long postId) {
        return store.values().stream()
                .filter(c -> Objects.equals(c.getPostId(), postId))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        store.remove(id);
    }

    public void deleteByPostId(Long postId) {
        store.values().removeIf(c -> Objects.equals(c.getPostId(), postId));
    }
}
