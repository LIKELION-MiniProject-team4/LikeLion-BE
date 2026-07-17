package com.likelion.miniproject.global.point;

public interface UserPointManager {
    /** 포인트를 차감한다. 잔여 포인트가 부족하면 false 반환 */
    boolean deduct(Long userId, int amount, PointReason reason);
}