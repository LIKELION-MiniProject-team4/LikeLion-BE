package com.likelion.miniproject.user;

import com.likelion.miniproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAuthUserLookupImpl implements AuthUserLookup {

    private final UserRepository userRepository;

    @Override
    public Optional<AuthUserInfo> findById(Long userId) {
        return userRepository.findById(userId)
                .map(user -> new AuthUserInfo(user.getId(), user.getUsername(), user.getRole().name()));
    }
}
