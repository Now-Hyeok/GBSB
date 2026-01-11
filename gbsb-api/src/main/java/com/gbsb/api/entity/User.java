package com.gbsb.api.entity;

import com.gbsb.api.entity.enums.UserRole;
import com.gbsb.api.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이메일은 계정 식별자 (OAuth, Local 공통)
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // 일반 로그인 사용자는 값 존재, OAuth 전용 계정은 null 허용
    @Column(length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OAuthAccount> oauthAccounts = new ArrayList<>();

    // ===== 생성 메서드 =====
    public static User createOAuthUser(String email) {
        User user = new User();
        user.email = email;
        user.role = UserRole.USER;
        user.status = UserStatus.ACTIVE;
        return user;
    }

    public static User createLocalUser(String email, String encodedPassword) {
        User user = new User();
        user.email = email;
        user.password = encodedPassword;
        user.role = UserRole.USER;
        user.status = UserStatus.ACTIVE;
        return user;
    }
}
