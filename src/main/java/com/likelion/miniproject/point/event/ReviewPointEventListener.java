package com.likelion.miniproject.point.event;

import com.likelion.miniproject.global.point.PointReason;
import com.likelion.miniproject.point.service.PointService;
import com.likelion.miniproject.review.event.ReviewWrittenEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewPointEventListener {

    private static final int REVIEW_WRITE_REWARD_POINT = 5;

    private final PointService pointService;

    // 리뷰 저장 트랜잭션이 커밋된 뒤에만 실행되어, 포인트 지급 실패가 리뷰 작성 자체를 롤백시키지 않는다.
    // @Async로 별도 스레드에서 실행해야 한다 - 같은 스레드에서 AFTER_COMMIT 직후 새 @Transactional을
    // 호출하면 이전 트랜잭션의 동기화 상태가 완전히 정리되기 전이라 변경사항이 flush/commit 되지 않고
    // 조용히 유실되는 문제가 있었다 (실제로 발생 확인함).
    // 지금은 실패 시 로그만 남기고 유실을 감수한다. 추후 retry/circuit breaker 도입 시 이 catch 블록만 손보면 된다.
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReviewWritten(ReviewWrittenEvent event) {
        log.info("event=review_written_point_event_received reviewId={} userId={}",
                event.reviewId(), event.userId());

        try {
            pointService.earn(event.userId(), REVIEW_WRITE_REWARD_POINT, PointReason.REVIEW_WRITE);
        } catch (RuntimeException e) {
            log.error("event=point_earn_failed userId={} reviewId={} reason={}",
                    event.userId(), event.reviewId(), PointReason.REVIEW_WRITE, e);
        }
    }
}
