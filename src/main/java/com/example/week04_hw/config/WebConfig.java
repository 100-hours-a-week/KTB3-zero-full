package com.example.week04_hw.config;

import com.example.week04_hw.support.LoginRequiredInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginRequiredInterceptor())
                // 로그인 필요한 영역 (화면용)
                .addPathPatterns(
                        "/posts/new",
                        "/posts/*/edit",

                        "/posts",        // POST (생성)
                        "/posts/*",      // PUT / DELETE (수정/삭제)

                        "/posts/*/comments",     // POST (생성)
                        "/posts/*/comments/*"    // PUT / DELETE (수정/삭제)
                )
                // 로그인 불필요(예외) 영역
                .excludePathPatterns(
                        "/users/**",             // 회원가입/로그인/로그아웃(폼+JSON 모두 허용)
                        "/css/**", "/js/**", "/images/**", "/static/**"
                );
    }
}
