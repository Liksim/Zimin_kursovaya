package ui;

import model.Cell;
import model.objects.*;
import ui.widgets.*;

public class WidgetFactory {

    private FieldWidget _fieldWidget;

    public FieldWidget fieldWidget() {
        return _fieldWidget;
    }

    public void setFieldWidget(FieldWidget fieldWidget) {
        _fieldWidget = fieldWidget;
    }

    public CellWidget create(FieldWidget fieldWidget, Cell cell) {
        if(_fieldWidget.cells().containsKey(cell)) return _fieldWidget.cells().get(cell);

        CellWidget cellWidget = new CellWidget(fieldWidget);

        for(Obstacle obstacle : cell.obstacles()) {
            if (obstacle instanceof Wall) {
                WallWidget widget = create((Wall) obstacle);
                cellWidget.addItem(widget);
            } else if (obstacle instanceof Headquarters) {
                HeadquartersWidget widget = create((Headquarters) obstacle);
                cellWidget.addItem(widget);
            } else if (obstacle instanceof Tank) {
                TankWidget widget = create((Tank) obstacle);
                cellWidget.addItem(widget);
            } else if (obstacle instanceof Water) {
                WaterWidget widget = create((Water) obstacle);
                cellWidget.addItem(widget);
            }
            else if (obstacle instanceof Jungle) {
                JungleWidget widget = create((Jungle) obstacle);
                cellWidget.addItem(widget);
            }
            else if (obstacle instanceof BarrelOfFuelOil) {
                BarrelOfFuelOilWidget widget = create((BarrelOfFuelOil) obstacle);
                cellWidget.addItem(widget);
            }
        }

        if(cell.obstacles().size() == 0) {
            cellWidget.addItem(new CellImage());
        }

        _fieldWidget.cells().put(cell, cellWidget);
        return cellWidget;
    }

    public CellWidget getWidget(Cell cell) {
        return _fieldWidget.cells().get(cell);
    }

    public WallWidget create(Wall wall) {
        if(_fieldWidget.walls().containsKey(wall)) return _fieldWidget.walls().get(wall);

        WallWidget item = new WallWidget(wall);
        _fieldWidget.walls().put(wall, item);
        wall.addObstacleListener(item);
        return item;
    }

    public WallWidget getWidget(Wall wall) {
        return _fieldWidget.walls().get(wall);
    }

    public void remove(Wall wall) {
        _fieldWidget.walls().remove(wall);
    }

    public WaterWidget create(Water water) {
        if(_fieldWidget.water().containsKey(water)) return _fieldWidget.water().get(water);

        WaterWidget item = new WaterWidget(water);
        _fieldWidget.water().put(water, item);
        water.addObstacleListener(item);
        return item;
    }

    public WaterWidget getWidget(Water water) {
        return _fieldWidget.water().get(water);
    }

    public HeadquartersWidget create(Headquarters headquarters) {
        if(_fieldWidget.headquarters().containsKey(headquarters)) return _fieldWidget.headquarters().get(headquarters);

        HeadquartersWidget item = new HeadquartersWidget(headquarters);
        _fieldWidget.headquarters().put(headquarters, item);
        headquarters.addObstacleListener(item);
        return item;
    }

    public HeadquartersWidget getWidget(Headquarters headquarters) {
        return _fieldWidget.headquarters().get(headquarters);
    }

    public void remove(Headquarters headquarters) {
        _fieldWidget.headquarters().remove(headquarters);
    }

    public TankWidget create(Tank tank) {
        if(_fieldWidget.tanks().containsKey(tank)) return _fieldWidget.tanks().get(tank);

        TankWidget item = new TankWidget(tank);
        _fieldWidget.tanks().put(tank, item);
        tank.addObstacleListener(item);
        tank.addTankActionListener(item);
        return item;
    }

    public TankWidget getWidget(Tank tank) {
        return _fieldWidget.tanks().get(tank);
    }

    public void remove(Tank tank) {
        _fieldWidget.tanks().remove(tank);
    }

    public BulletWidget create(Bullet bullet) {
        BulletWidget item = new BulletWidget(bullet);
        bullet.addObstacleListener(item);
        bullet.addMovingListener(item);
        return item;
    }

    public JungleWidget create(Jungle jungle) {
        if(_fieldWidget.jungles().containsKey(jungle)) return _fieldWidget.jungles().get(jungle);

        JungleWidget item = new JungleWidget(jungle);
        _fieldWidget.jungles().put(jungle, item);
        jungle.addObstacleListener(item);
        return item;
    }

    public JungleWidget getWidget(Jungle jungle) {
        return _fieldWidget.jungles().get(jungle);
    }

    public void remove(Jungle jungle) {
        _fieldWidget.jungles().remove(jungle);
    }

    public BarrelOfFuelOilWidget create(BarrelOfFuelOil barrelOfFuelOil) {
        if(_fieldWidget.barrelsOfFuelOil().containsKey(barrelOfFuelOil)) return _fieldWidget.barrelsOfFuelOil().get(barrelOfFuelOil);

        BarrelOfFuelOilWidget item = new BarrelOfFuelOilWidget(barrelOfFuelOil);
        _fieldWidget.barrelsOfFuelOil().put(barrelOfFuelOil, item);
        barrelOfFuelOil.addObstacleListener(item);
        return item;
    }

    public BarrelOfFuelOilWidget getWidget(BarrelOfFuelOil barrelOfFuelOil) {
        return _fieldWidget.barrelsOfFuelOil().get(barrelOfFuelOil);
    }

    public void remove(BarrelOfFuelOil barrelOfFuelOil) {
        _fieldWidget.barrelsOfFuelOil().remove(barrelOfFuelOil);
    }
}
