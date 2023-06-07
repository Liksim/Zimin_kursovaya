package event;

import java.util.EventListener;

public interface GameListener extends EventListener {

    void bulletBorn(BulletEvent event);
    void tankActivityChanged(GameEvent event);
    void turnPassed(GameEvent event);
    void winnerDetermined(GameEvent event);
}
