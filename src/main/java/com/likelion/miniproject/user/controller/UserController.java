package com.likelion.miniproject.user.controller;

import com.likelion.miniproject.global.response.GlobalApiResponse;
import com.likelion.miniproject.global.security.jwt.JwtProperties;
import com.likelion.miniproject.user.controller.request.LoginRequest;
import com.likelion.miniproject.user.controller.request.SignupRequest;
import com.likelion.miniproject.user.controller.response.LoginResponse;
import com.likelion.miniproject.user.controller.response.SignupResponse;
import com.likelion.miniproject.user.controller.response.UserResponseCode;
import com.likelion.miniproject.global.security.jwt.AuthUser;
import com.likelion.miniproject.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@Tag(name = "User", description = "회원가입 / 로그인 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtProperties jwtProperties;

    @Operation(summary = "회원가입", description = "username(이메일), password, name, nickname, phone을 받아 회원가입을 처리한다. 성공 시 point 20이 지급된다.")
    @PostMapping("/signup")
    public ResponseEntity<GlobalApiResponse<SignupResponse>> signup(@Valid @RequestBody SignupRequest request) {
        SignupResponse response = userService.signup(request);
        return ResponseEntity.status(201)
                .body(GlobalApiResponse.created(UserResponseCode.SIGNUP_SUCCESS, response));
    }

    @Operation(summary = "로그인", description = "accessToken은 응답 바디로, refreshToken은 httpOnly 쿠키로 발급한다.")
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

    @Operation(summary = "로그아웃", description = "서버에 저장된 refreshToken을 무효화하고 쿠키를 즉시 만료시킨다.")
    @PostMapping("/logout")
    public ResponseEntity<GlobalApiResponse<Void>> logout(@AuthenticationPrincipal AuthUser authUser) {
        userService.logout(authUser.userId());

        ResponseCookie expiredCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, expiredCookie.toString())
                .body(GlobalApiResponse.ok(UserResponseCode.LOGOUT_SUCCESS));
    }
}
