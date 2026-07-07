package com.likelion.miniproject.user;

import java.util.Optional;

/**
 * 각 프로젝트의 User 서비스/리포지토리에서 구현해야 하는 단 하나의 인터페이스.
 *
 * 예)
 * {@code
 * @Component
 * @RequiredArgsConstructor
 * public class AuthUserLookupImpl implements AuthUserLookup {
 *     private final UserRepository userRepository;
 *
 *     public Optional<AuthUserInfo> findById(Long userId) {
 *         return userRepository.findById(userId)
 *                 .map(u -> new AuthUserInfo(u.getId(), u.getUsername(), u.getRole().name()));
 *     }
 * }
 * }
 */
public interface AuthUserLookup {
    Optional<AuthUserInfo> findById(Long userId);
}
