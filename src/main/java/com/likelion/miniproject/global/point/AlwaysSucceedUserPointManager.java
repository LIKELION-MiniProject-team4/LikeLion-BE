
import org.springframework.stereotype.Component;

/**
 * TODO: User 도메인에서 실제 포인트 적립/차감 로직으로 교체할 것.
 * 지금은 항상 성공 처리하고, 적립은 아무 동작도 하지 않는다.
 */
@Component
public class AlwaysSucceedUserPointManager implements UserPointManager {

    @Override
    public boolean deduct(Long userId, int amount) {
        return true;
    }

    @Override
    public void earn(Long userId, int amount) {
        // TODO: 실제 포인트 적립 로직 구현 필요
    }
}