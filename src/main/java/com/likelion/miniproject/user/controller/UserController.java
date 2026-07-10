package com.likelion.miniproject.user.controller;

import com.likelion.miniproject.global.response.GlobalApiResponse;
import com.likelion.miniproject.global.security.jwt.JwtProperties;
import com.likelion.miniproject.user.controller.request.LoginRequest;
import com.likelion.miniproject.user.controller.request.SignupRequest;
import com.likelion.miniproject.user.controller.response.LoginResponse;
import com.likelion.miniproject.user.controller.response.SignupResponse;
import com.likelion.miniproject.user.controller.response.UserResponseCode;
import com.likelion.miniproject.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtProperties jwtProperties;

    @PostMapping("/signup")
    public ResponseEntity<GlobalApiResponse<SignupResponse>> signup(@Valid @RequestBody SignupRequest request) {
        SignupResponse response = userService.signup(request);
        return ResponseEntity.status(201)
                .body(GlobalApiResponse.created(UserResponseCode.SIGNUP_SUCCESS, response));
    }

    @PostMapping("/login")
    public ResponseEntity<GlobalApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        UserService.LoginResult result = userService.login(request);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", result.refreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofMillis(jwtProperties.getRefreshTokenExpiration()))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(GlobalApiResponse.ok(UserResponseCode.LOGIN_SUCCESS, new LoginResponse(result.accessToken(), result.role())));
    }
}
