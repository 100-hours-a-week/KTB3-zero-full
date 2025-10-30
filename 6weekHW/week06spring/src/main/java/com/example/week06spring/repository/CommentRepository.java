package com.example.week06spring.repository;

import com.example.week06spring.dto.CommentDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentRepository {

    private final Map<Long, CommentDto> commentRepository = new LinkedHashMap<>();
    long sequence = 0l;

    public CommentDto save(CommentDto commentDto) {
        commentDto.setCommentId(sequence++);
        commentRepository.put(commentDto.getCommentId(), commentDto);
        return commentDto;
    }

    public CommentDto findById(Long id) {
        return commentRepository.get(id);
    } //id로 댓글 찾기


    //특정 게시글(postId)에 달린 모든 댓글 조회
    public List<CommentDto> findAllByPostId(Long postId) {

        List<CommentDto> matchingComments = new ArrayList<>(); //postId가 일치하는 댓글만 담을 빈 리스트

        List<CommentDto> allCommentsList = new ArrayList<>(commentRepository.values()); //commentRepository 전체 리스트로 변환

        for (int i = 0; i < allCommentsList.size(); i++) {
            CommentDto comment = allCommentsList.get(i);
            if (comment.getPostId().equals(postId)) {
                matchingComments.add(comment);
            }
        }
        return matchingComments;
    }

    public void delete(Long id) {
        commentRepository.remove(id);
    } //id 댓글 삭제

    public CommentDto update(Long id, CommentDto commentDto) { //id 댓글 수정
        commentRepository.replace(id, commentDto);
        return commentDto;
    }

    @PostConstruct
    public void init() {
        if(commentRepository.isEmpty()) {
            save(new CommentDto(null, 0L, "댓글러1", "첫 댓글입니다!"));
            save(new CommentDto(null, 0L, "댓글러2", "반가워요~"));
            save(new CommentDto(null, 1L, "댓글러3", "테스트 중이시군요."));
        }
    }
}

