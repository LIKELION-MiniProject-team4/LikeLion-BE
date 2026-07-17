package com.likelion.miniproject.point.event;

import com.likelion.miniproject.global.point.PointReason;
import com.likelion.miniproject.point.service.PointService;
import com.likelion.miniproject.tag.event.TagClickedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class TagPointEventListener {

    private static final int TAG_CLICK_REWARD_POINT = 2;

    private final PointService pointService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTagClicked(TagClickedEvent event) {
        log.info("event=tag_clicked_point_event_received tagClickId={} userId={} professorId={}",
                event.tagClickId(), event.userId(), event.professorId());

        try {
            pointService.earn(event.userId(), TAG_CLICK_REWARD_POINT, PointReason.TAG_CLICK);
        } catch (RuntimeException e) {
            log.error("event=point_earn_failed userId={} tagClickId={} reason={}",
                    event.userId(), event.tagClickId(), PointReason.TAG_CLICK, e);
        }
    }
}
