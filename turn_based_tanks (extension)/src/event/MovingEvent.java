package event;

import model.Cell;
import model.objects.Obstacle;

import java.util.EventObject;

public class MovingEvent extends EventObject {

    private Obstacle _obstacle;
    private Cell _fromCell;
    private Cell _toCell;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public MovingEvent(java.lang.Object source) {
        super(source);
    }

    public Obstacle object() {
        return _obstacle;
    }

    public void setObject(Obstacle obstacle) {
        _obstacle = obstacle;
    }

    public Cell fromCell() {
        return _fromCell;
    }

    public void setFromCell(Cell fromCell) {
        _fromCell = fromCell;
    }

    public Cell toCell() {
        return _toCell;
    }

    public void setToCell(Cell toCell) {
        _toCell = toCell;
    }
}
