package event;

import model.objects.Object;

import java.util.EventObject;

public class DamageableObjectEvent extends EventObject {

    private Object _object;

    private int _damage;

    public void setObject(Object object) {
        _object = object;
    }

    public Object object() {
        return _object;
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
    public DamageableObjectEvent(Object source) {
        super(source);
    }
}
