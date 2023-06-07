package ui.widgets;

import event.*;
import model.Cell;
import model.Field;
import model.objects.*;
import ui.WidgetFactory;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class FieldWidget extends JPanel implements GameListener {

    private final Field _field;
    private final WidgetFactory _widgetFactory;
    private final Map<Cell, CellWidget> _cells = new HashMap<>();
    public Map<Cell, CellWidget> cells() {
        return _cells;
    }
    private final Map<Wall, WallWidget> _walls = new HashMap<>();
    public Map<Wall, WallWidget> walls() {
        return _walls;
    }
    private final Map<Water, WaterWidget> _water = new HashMap<>();
    public Map<Water, WaterWidget> water() {
        return _water;
    }
    private final Map<Headquarters, HeadquartersWidget> _headquarters = new HashMap<>();
    public Map<Headquarters, HeadquartersWidget> headquarters() {
        return _headquarters;
    }
    private final Map<Tank, TankWidget> _tanks = new HashMap<>();
    public Map<Tank, TankWidget> tanks() {
        return _tanks;
    }
    private final Map<Jungle, JungleWidget> _jungles = new HashMap<>();
    public Map<Jungle, JungleWidget> jungles() {
        return _jungles;
    }
    private final Map<BarrelOfFuelOil, BarrelOfFuelOilWidget> _barrelsOfFuelOil = new HashMap<>();
    public Map<BarrelOfFuelOil, BarrelOfFuelOilWidget> barrelsOfFuelOil() {
        return _barrelsOfFuelOil;
    }

    public FieldWidget(Field field, WidgetFactory widgetFactory) {
        _field = field;
        _widgetFactory = widgetFactory;
        _widgetFactory.setFieldWidget(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void fillField() {
        for (int i = 0; i < _field.height(); ++i) {
            JPanel row = createRow(i);
            add(row);
        }
    }

    private JPanel createRow(int rowIndex) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        for(int i = 0; i < _field.width(); ++i) {
            Cell cell = _field.cells()[rowIndex][i];
            CellWidget cellWidget = _widgetFactory.create(this, cell);
            row.add(cellWidget);
        }
        return row;
    }

    //---------------Сигналы---------------

    @Override
    public void bulletBorn(BulletEvent event) {
        Bullet bullet = event.bullet();
        BulletWidget bulletWidget = _widgetFactory.create(bullet);
        _cells.get(bullet.cell()).addItem(bulletWidget);
        System.out.println("Bullet born");
    }

    @Override
    public void tankActivityChanged(GameEvent event) {
        _tanks.get(event.tank()).setActive(event.isActive());
    }

    @Override
    public void turnPassed(GameEvent event) {
        for(TankWidget tankWidget : _tanks.values()) {
            if(_tanks.get(event.tank()) == tankWidget) {
                tankWidget.setActive(event.isActive());
            }
            else {
                tankWidget.setActive(false);
            }
        }
    }

    @Override
    public void winnerDetermined(GameEvent event) {
    }
}
