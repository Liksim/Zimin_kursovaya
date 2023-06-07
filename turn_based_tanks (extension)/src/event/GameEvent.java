package event;

import model.objects.Tank;

import java.util.EventObject;

public class GameEvent extends EventObject {

    private Tank _tank;
    private boolean _isActive;

    public Tank tank() {
        return _tank;
    }

    public void setTank(Tank tank) {
        _tank = tank;
    }

    public boolean isActive() {
        return _isActive;
    }

    public void setIsActive(boolean isActive) {
        _isActive = isActive;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public GameEvent(Object source) {
        super(source);
    }
}
