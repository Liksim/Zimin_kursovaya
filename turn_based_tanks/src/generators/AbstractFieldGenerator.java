package generators;

import model.Field;
import event.ObjectListener;

import java.util.EventListener;

public abstract class AbstractFieldGenerator {
    public abstract Field generateField(EventListener listener);
}
