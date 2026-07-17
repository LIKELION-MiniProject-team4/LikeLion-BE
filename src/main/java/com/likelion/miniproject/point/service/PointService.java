package com.likelion.miniproject.point.service;

import com.likelion.miniproject.global.point.PointReason;
import com.likelion.miniproject.global.point.UserPointManager;
import com.likelion.miniproject.point.controller.response.PointHistoryResponse;
import com.likelion.miniproject.point.entity.PointHistory;
import com.likelion.miniproject.point.repository.PointHistoryRepository;
import com.likelion.miniproject.user.entity.User;
import com.likelion.miniproject.user.exception.UserNotFoundException;
import com.likelion.miniproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointService implements UserPointManager {

    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public void earn(Long userId, int amount, PointReason reason) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        user.increasePoint(amount);
        pointHistoryRepository.save(PointHistory.create(userId, amount, reason));

        log.info("event=point_earned userId={} amount={} reason={} balance={}", userId, amount, reason, user.getPoint());
    }

    @Override
    @Transactional
    public boolean deduct(Long userId, int amount, PointReason reason) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (!user.tryDecreasePoint(amount)) {
            return false;
        }

        pointHistoryRepository.save(PointHistory.create(userId, -amount, reason));

        log.info("event=point_deducted userId={} amount={} reason={} balance={}", userId, amount, reason, user.getPoint());

        return true;
    }

    @Transactional(readOnly = true)
    public List<PointHistoryResponse> getMyPointHistory(Long userId) {
        return pointHistoryRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(PointHistoryResponse::from)
                .toList();
    }
}
