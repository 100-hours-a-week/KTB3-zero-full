package kr.adapterz.jpa_practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    @GetMapping("/posts") //http://localhost:8080/posts
    public String postsPage() { // templates/posts.html
        return "posts";
    }

    @GetMapping("/posts/new")
    public String newPostPage() {
        return "post-new";
    }

    @GetMapping("/posts/{postId}") //게시글 목록에서 {postId} 누르면
    public String postDetailPage(@PathVariable Long postId) {
        return "post-detail";
    }
}
