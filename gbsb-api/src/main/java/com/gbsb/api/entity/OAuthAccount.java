package com.gbsb.api.entity;

import com.gbsb.api.entity.enums.OAuthProvider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "oauth_account",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"provider", "providerUserId"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OAuthProvider provider;

    // Google sub, Kakao id, Naver id
    @Column(nullable = false, length = 100)
    private String providerUserId;

    // 토큰 저장이 필요할 경우만 (보통 refresh token만 저장)
    @Column(length = 500)
    private String refreshToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static OAuthAccount create(
            OAuthProvider provider,
            String providerUserId,
            User user
    ) {
        OAuthAccount account = new OAuthAccount();
        account.provider = provider;
        account.providerUserId = providerUserId;
        account.user = user;
        return account;
    }
}
