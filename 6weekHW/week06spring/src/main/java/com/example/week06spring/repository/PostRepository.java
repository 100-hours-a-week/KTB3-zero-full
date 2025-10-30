package com.example.week06spring.repository;
import java.util.*;
import com.example.week06spring.dto.PostDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;


@Repository
public class PostRepository {

    private final Map<Long, PostDto> postRepository = new LinkedHashMap<> (); //hashmap 생성
    long sequence = 0l;

    public PostDto save(PostDto postDto) { //PostDto 저장 메서드
        postDto.setPostId(sequence++); //sequence 1 증가시켜서 postId에 대입
        postRepository.put(postDto.getPostId(), postDto);
        return postDto;
    }

    public void delete(Long id) {
        postRepository.remove(id);
    } //게시글 삭제

    public PostDto update(Long id, PostDto postDto) {
        postRepository.replace(id, postDto);
        return postDto;
    }

    public PostDto findById(Long id) { //id로 특정 PostDto 조회
        return postRepository.get(id);
    } //id 게시글 찾기

    public List<PostDto> findAll() { //모든 PostDto 조회
        List<PostDto> allPostsList = new ArrayList<>(postRepository.values()); //PostRepository 전체 리스트로 변환
        return allPostsList;
    }

    @PostConstruct
    public void init() { //초기 생성
        if(postRepository.isEmpty()) { //repository 비어 있을 때 초기값
            save(new PostDto(null, "안녕하세요", "제로", "잘 부탁합니다."));
            save(new PostDto(null, "테스트", "홍길동", "잘 보이나요?? 댓글 부탁합니다"));
        }
    }




}
