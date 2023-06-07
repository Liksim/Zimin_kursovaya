package event;

import model.Cell;
import model.objects.Object;

import java.util.EventObject;

public class ObjectEvent extends EventObject {

    private Object _object;

    private Cell _cell;

    public void setObject(Object object) {
        _object = object;
    }

    public Object object() {
        return _object;
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
    public ObjectEvent(java.lang.Object source) {
        super(source);
    }
}
