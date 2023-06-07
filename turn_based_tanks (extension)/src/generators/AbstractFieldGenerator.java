package generators;

import model.Field;
import event.ObstacleListener;

public abstract class AbstractFieldGenerator {
    public abstract Field generateField(ObstacleListener listener);
}
