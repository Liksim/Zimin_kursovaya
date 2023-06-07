package event;

import model.objects.Bullet;
import model.objects.Tank;

import java.util.EventObject;

public class TankActionEvent extends EventObject {

    private Tank _tank;

    public Tank tank() {
        return _tank;
    }

    public void setTank(Tank tank) {
        _tank = tank;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public TankActionEvent(Object source) {
        super(source);
    }
}
