package event;

import model.objects.Obstacle;

import java.util.EventObject;

public class DamageEvent extends EventObject {

    private Obstacle _obstacle;

    private int _damage;

    public void setObject(Obstacle obstacle) {
        _obstacle = obstacle;
    }

    public Obstacle object() {
        return _obstacle;
    }

    public void setDamage(int damage) {
        _damage = damage;
    }

    public int damage() {
        return _damage;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public DamageEvent(Obstacle source) {
        super(source);
    }
}
