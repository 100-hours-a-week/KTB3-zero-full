package com.example.week04_hw.controller;

import com.example.week04_hw.dto.CommentDto;
import com.example.week04_hw.service.CommentService;
import com.example.week04_hw.service.PostService;
import com.example.week04_hw.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Controller
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    public CommentController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/{commentId}/edit")
    public String editForm(@PathVariable Long postId, @PathVariable Long commentId, Model model, HttpSession session) {
        var comment = commentService.get(commentId);
        var user = userService.currentUserBySession(session).orElseThrow();
        if (!Objects.equals(comment.getPostId(), postId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "COMMENT_POST_MISMATCH");
        }
        if (!Objects.equals(comment.getAuthorUserId(), user.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT_OWNER");
        }
        model.addAttribute("postId", postId);
        model.addAttribute("commentDto", comment);
        return "comments/edit";
    }

    @PostMapping
    public String create(@PathVariable Long postId,
                         @ModelAttribute("commentDto") CommentDto form,
                         HttpSession session) {
        var user = userService.currentUserBySession(session).orElseThrow();
        form.setAuthor(user.getDisplayName() != null ? user.getDisplayName() : user.getUsername());
        form.setAuthorUserId(user.getUserId());
        commentService.create(postId, form);
        return "redirect:/posts/" + postId;
    }

    @PutMapping("/{commentId}")
    public String update(@PathVariable Long postId,
                         @PathVariable Long commentId,
                         @ModelAttribute("commentDto") CommentDto form,
                         HttpSession session) {
        var user = userService.currentUserBySession(session).orElseThrow();
        commentService.update(postId, commentId, form, user.getUserId());
        return "redirect:/posts/" + postId;
    }

    @DeleteMapping("/{commentId}")
    public String delete(@PathVariable Long postId,
                         @PathVariable Long commentId,
                         HttpSession session) {
        var user = userService.currentUserBySession(session).orElseThrow();
        commentService.delete(postId, commentId, user.getUserId());
        return "redirect:/posts/" + postId;
    }
}
