package com.likelion.miniproject.global.point;

import org.springframework.stereotype.Component;

/**
 * TODO: User 도메인에서 실제 포인트 차감 로직으로 교체할 것.
 * 지금은 항상 차감 성공한 것으로 처리한다.
 */
@Component
public class AlwaysSucceedUserPointManager implements UserPointManager {
    @Override
    public boolean deduct(Long userId, int amount) {
        return true;
    }
}