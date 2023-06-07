package event;

import model.Cell;
import model.objects.Obstacle;

import java.util.EventObject;

public class DieEvent extends EventObject {

    private Obstacle _obstacle;

    private Cell _cell;

    public void setObject(Obstacle obstacle) {
        _obstacle = obstacle;
    }

    public Obstacle object() {
        return _obstacle;
    }

    public void setCell(Cell cell) {
        _cell = cell;
    }

    public Cell cell() {
        return _cell;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public DieEvent(java.lang.Object source) {
        super(source);
    }
}
