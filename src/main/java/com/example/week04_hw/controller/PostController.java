package com.example.week04_hw.controller;

import com.example.week04_hw.dto.CommentDto;
import com.example.week04_hw.dto.PostDto;
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
@RequestMapping("/posts")
public class PostController {
    private final PostService service;
    private final CommentService commentService;
    private final UserService userService;

    public PostController(PostService service, CommentService commentService, UserService userService) {
        this.service = service;
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", service.getAllPosts());
        return "posts/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        var post = service.getPostById(id);
        model.addAttribute("post", post);
        model.addAttribute("comments", commentService.listByPost(id));
        model.addAttribute("commentDto", new CommentDto());
        return "posts/detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("postDto", new PostDto());
        return "posts/form";
    }

    @PostMapping
    public String create(@ModelAttribute("postDto") PostDto form, HttpSession session) {
        var user = userService.currentUserBySession(session).orElseThrow();
        form.setAuthor(user.getDisplayName() != null ? user.getDisplayName() : user.getUsername());
        form.setAuthorUserId(user.getUserId());
        service.create(form);
        return "redirect:/posts";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        var post = service.getPostById(id);
        var user = userService.currentUserBySession(session).orElseThrow();
        if (!Objects.equals(post.getAuthorUserId(), user.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT_OWNER");
        }
        model.addAttribute("postDto", post);
        return "posts/edit";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("postDto") PostDto form,
                         HttpSession session) {
        var user = userService.currentUserBySession(session).orElseThrow();
        service.update(id, form, user.getUserId());
        return "redirect:/posts/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        var user = userService.currentUserBySession(session).orElseThrow();
        service.delete(id, user.getUserId());
        return "redirect:/posts";
    }
}
