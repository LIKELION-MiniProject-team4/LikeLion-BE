package com.likelion.miniproject.user;

import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 임시 구현체. User 도메인(엔티티/회원가입/로그인) 완성되면
 * 이 클래스는 지우고 실제 UserRepository 기반 AuthUserLookup 구현체로 교체할 것.
 */
@Component
public class StubAuthUserLookup implements AuthUserLookup {

    @Override
    public Optional<AuthUserInfo> findById(Long userId) {
        return Optional.empty();
    }
}
