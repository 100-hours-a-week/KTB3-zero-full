package com.example.week06spring.service;

import com.example.week06spring.dto.CommentDto;
import com.example.week06spring.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    //postId 게시글의 모든 댓글 조회
    public List<CommentDto> getCommentsByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    //postId 게시글에 댓글 달기
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        commentDto.setPostId(postId);
        return commentRepository.save(commentDto);
    }

    //commentId 게시글 조회
    public CommentDto getCommentById(Long id) {
        CommentDto dto = commentRepository.findById(id);
        if(dto==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 댓글"); // 404 존재하지 않는 댓글 조회 실패
        } else {
            return dto; //200 존재하는 댓글 조회 성공
        }
    }

    //댓글 삭제
    public void deleteComment(Long id) {
        getCommentById(id); //댓글 존재 확인
        commentRepository.delete(id);
    }

    // 댓글 수정
    public CommentDto updateComment(Long commentId, CommentDto commentDto) {
        commentDto.setCommentId(commentId); // ID 보장
        return commentRepository.update(commentId, commentDto);
    }
}