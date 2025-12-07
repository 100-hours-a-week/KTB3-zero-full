package kr.adapterz.jpa_practice.security;

import kr.adapterz.jpa_practice.entity.user.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = "ROLE_" + user.getUserRole().name(); // USER -> ROLE_USER, ADMIN -> ROLE_ADMIN
        return List.of(new SimpleGrantedAuthority(roleName));
    }

    // 나머지 isAccountNonExpired, isAccountNonLocked 등은 일단 true 리턴하게 해도 됨
}
