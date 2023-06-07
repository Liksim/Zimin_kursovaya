package event;

import java.util.EventListener;

public interface MovingListener extends EventListener {
    void objectMoved(MovingEvent event);
}