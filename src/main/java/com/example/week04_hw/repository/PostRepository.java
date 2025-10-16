package com.example.week04_hw.repository;

import com.example.week04_hw.dto.PostDto;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.*;

@Repository
public class PostRepository {
    private final Map<Long, PostDto> store = new LinkedHashMap<>();
    private long sequence = 0L;

    @PostConstruct
    public void init() {
        if (store.isEmpty()) {
            save(new PostDto(null, "첫 글", "제로", "스프링 커뮤니티 시작!", "spring,hello"));
            save(new PostDto(null, "카테부 화이팅", "라이언", "카테부 3기 10월에도 화이팅", "KTB"));
            save(new PostDto(null, "패션과 날씨", "Amy", "OOTD는 기온 영향을 크게 받음", "fashion,weather"));
        }
    }

    public PostDto save(PostDto post) {
        if (post.getId() == null) {
            sequence++;
            post.setId(sequence);
        } else if (post.getId() > sequence) {
            sequence = post.getId();
        }
        store.put(post.getId(), post);
        return post;
    }

    public Optional<PostDto> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<PostDto> findAll() {
        return new ArrayList<>(store.values());
    }

    public void deleteById(Long id) {
        store.remove(id);
    }
}

