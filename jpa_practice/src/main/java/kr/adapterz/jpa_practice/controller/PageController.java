package kr.adapterz.jpa_practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/posts") //http://localhost:8080/posts
    public String postsPage() { // templates/posts.html
        return "posts";
    }
}
