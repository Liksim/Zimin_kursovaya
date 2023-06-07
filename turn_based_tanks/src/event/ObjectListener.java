package event;

import model.objects.Object;

import java.util.EventListener;

public interface ObjectListener extends EventListener {
    void objectDied(ObjectEvent event);
}
