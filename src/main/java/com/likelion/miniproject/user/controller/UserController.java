package com.likelion.miniproject.user.controller;

import com.likelion.miniproject.global.response.GlobalApiResponse;
import com.likelion.miniproject.global.security.jwt.JwtProperties;
import com.likelion.miniproject.user.controller.request.LoginRequest;
import com.likelion.miniproject.user.controller.request.SignupRequest;
import com.likelion.miniproject.user.controller.request.UserUpdateRequest;
import com.likelion.miniproject.user.controller.response.LoginResponse;
import com.likelion.miniproject.user.controller.response.SignupResponse;
import com.likelion.miniproject.user.controller.response.UserMeResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@Tag(name = "User", description = "회원가입 / 로그인 / 내 정보 API")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtProperties jwtProperties;

    @Operation(summary = "회원가입", description = "username(이메일), password, name, nickname, phone을 받아 회원가입을 처리한다. 성공 시 point 20이 지급된다.")
    @PostMapping("/api/auth/signup")
    public ResponseEntity<GlobalApiResponse<SignupResponse>> signup(@Valid @RequestBody SignupRequest request) {
        SignupResponse response = userService.signup(request);
        return ResponseEntity.status(201)
                .body(GlobalApiResponse.created(UserResponseCode.SIGNUP_SUCCESS, response));
    }

    @Operation(summary = "로그인", description = "accessToken은 응답 바디로, refreshToken은 httpOnly 쿠키로 발급한다.")
    @PostMapping("/api/auth/login")
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
    @PostMapping("/api/auth/logout")
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

    @Operation(summary = "내 정보 조회", description = "로그인한 사용자 본인의 정보를 조회한다.")
    @GetMapping("/api/users/me")
    public ResponseEntity<GlobalApiResponse<UserMeResponse>> getMe(@AuthenticationPrincipal AuthUser authUser) {
        UserMeResponse response = userService.getMe(authUser.userId());
        return ResponseEntity.ok(GlobalApiResponse.ok(UserResponseCode.GET_ME_SUCCESS, response));
    }

    @Operation(summary = "내 정보 수정", description = "이름, 닉네임, 비밀번호를 부분 수정한다. 요청에 포함하지 않은 필드는 기존 값을 유지한다.")
    @PatchMapping("/api/users/me")
    public ResponseEntity<GlobalApiResponse<UserMeResponse>> updateMe(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        UserMeResponse response = userService.updateMe(authUser.userId(), request);
        return ResponseEntity.ok(GlobalApiResponse.ok(UserResponseCode.UPDATE_ME_SUCCESS, response));
    }
}
