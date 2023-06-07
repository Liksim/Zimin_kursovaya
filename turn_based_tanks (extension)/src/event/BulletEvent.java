package event;

import model.objects.Bullet;

import java.util.EventObject;

public class BulletEvent extends EventObject {

    private Bullet _bullet;

    public Bullet bullet() {
        return _bullet;
    }

    public void setBullet(Bullet bullet) {
        _bullet = bullet;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public BulletEvent(Object source) {
        super(source);
    }
}
