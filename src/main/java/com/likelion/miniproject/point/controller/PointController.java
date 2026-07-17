package com.likelion.miniproject.point.controller;

import com.likelion.miniproject.global.response.GlobalApiResponse;
import com.likelion.miniproject.global.security.jwt.AuthUser;
import com.likelion.miniproject.point.controller.response.PointHistoryResponse;
import com.likelion.miniproject.point.controller.response.PointResponseCode;
import com.likelion.miniproject.point.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Point", description = "포인트 내역 조회 API")
@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @Operation(summary = "내 포인트 내역 조회", description = "로그인한 사용자의 포인트 적립/차감 이력을 최신순으로 반환한다.")
    @GetMapping("/api/points/me")
    public ResponseEntity<GlobalApiResponse<List<PointHistoryResponse>>> getMyPointHistory(@AuthenticationPrincipal AuthUser authUser) {
        List<PointHistoryResponse> result = pointService.getMyPointHistory(authUser.userId());
        return ResponseEntity.ok(GlobalApiResponse.ok(PointResponseCode.MY_POINT_HISTORY_FETCHED, result));
    }
}
