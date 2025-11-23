package kr.adapterz.jpa_practice;

import kr.adapterz.jpa_practice.entity.User;
import kr.adapterz.jpa_practice.service.PostService;
import kr.adapterz.jpa_practice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InitSampleDataTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Test
    void initSampleData() {
        // 1. 유저 두 명 생성
        User user1 = userService.create(
                "alice@example.com",
                "password1",
                "아무 말쟁이 1"
        );

        User user2 = userService.create(
                "bob@example.com",
                "password2",
                "아무 말쟁이 2"
        );

        // 2. user1이 쓴 게시글 두 개
        postService.create(
                user1.getId(),
                "오늘의 아무 말",
                "오늘은 스프링 부트랑 씨름하는 중..."
        );

        postService.create(
                user1.getId(),
                "점심 뭐 먹지",
                "JPA 공부하다 보니 벌써 점심시간이네."
        );

        // 3. user2가 쓴 게시글 한 개
        postService.create(
                user2.getId(),
                "JPA 너무 헷갈린다",
                "엔티티, DTO, 컨트롤러, 서비스... 머리가 아프다."
        );
    }
}
