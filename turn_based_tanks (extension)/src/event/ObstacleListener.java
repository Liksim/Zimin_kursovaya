package event;

import java.util.EventListener;

public interface ObstacleListener extends EventListener {
    void obstacleDied(DieEvent event);
    void obstacleTakenDamage(DamageEvent event);
}
