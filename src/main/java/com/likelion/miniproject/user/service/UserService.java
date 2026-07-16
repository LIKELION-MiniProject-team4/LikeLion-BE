package com.likelion.miniproject.user.service;

import com.likelion.miniproject.global.security.jwt.JwtTokenProvider;
import com.likelion.miniproject.user.controller.request.LoginRequest;
import com.likelion.miniproject.user.controller.request.SignupRequest;
import com.likelion.miniproject.user.controller.request.UserUpdateRequest;
import com.likelion.miniproject.user.controller.response.SignupResponse;
import com.likelion.miniproject.user.controller.response.UserMeResponse;
import com.likelion.miniproject.user.entity.User;
import com.likelion.miniproject.user.exception.DuplicateUsernameException;
import com.likelion.miniproject.user.exception.InvalidCredentialsException;
import com.likelion.miniproject.user.exception.UserNotFoundException;
import com.likelion.miniproject.user.repository.UserRepository;
import com.likelion.miniproject.user.token.RefreshToken;
import com.likelion.miniproject.user.token.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public record LoginResult(String accessToken, String refreshToken, String role) {
    }

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicateUsernameException();
        }

        User user = User.create(
                request.username(),
                passwordEncoder.encode(request.password()),
                request.name(),
                request.nickname(),
                request.phone()
        );
        userRepository.save(user);

        log.info("event=user_signup_succeed userId={}", user.getId());

        return new SignupResponse(user.getId(), user.getUsername());
    }

    @Transactional
    public LoginResult login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getUsername(), user.getRole().name());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId(), user.getUsername());

        refreshTokenRepository.findByUserId(user.getId())
                .ifPresentOrElse(
                        existing -> existing.rotate(refreshToken),
                        () -> refreshTokenRepository.save(RefreshToken.create(user.getId(), refreshToken))
                );

        log.info("event=user_login_succeed userId={}", user.getId());

        return new LoginResult(accessToken, refreshToken, user.getRole().name());
    }

    @Transactional
    public void logout(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);

        log.info("event=user_logout_succeed userId={}", userId);
    }

    public UserMeResponse getMe(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return UserMeResponse.from(user);
    }

    @Transactional
    public UserMeResponse updateMe(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (request.name() != null) {
            user.updateName(request.name());
        }
        if (request.nickname() != null) {
            user.updateNickname(request.nickname());
        }
        if (request.newPassword() != null) {
            user.updatePassword(passwordEncoder.encode(request.newPassword()));
        }

        log.info("event=user_update_me_succeed userId={}", userId);

        return UserMeResponse.from(user);
    }
}
