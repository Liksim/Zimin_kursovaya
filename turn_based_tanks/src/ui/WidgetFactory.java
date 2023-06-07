package ui;

import model.Cell;
import model.objects.*;
import model.objects.Object;
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

        LyingObject lyingObject = cell.lyingObject();

        if(lyingObject != null) {
            if(lyingObject instanceof Water) {
                WaterWidget waterWidget = create((Water) lyingObject);
                cellWidget.addItem(waterWidget);
            }
        }

        Object notLyingObject = cell.notLyingObject();
        if(notLyingObject != null) {
            if(notLyingObject instanceof Wall) {
                WallWidget wallWidget = create((Wall) notLyingObject);
                cellWidget.addItem(wallWidget);
                //cellWidget.addItem(new DamageWidget());
            }
            else if(notLyingObject instanceof Headquarters) {
                HeadquartersWidget headquartersWidget = create((Headquarters) notLyingObject);
                cellWidget.addItem(headquartersWidget);
            }
            else if(notLyingObject instanceof Tank) {
                TankWidget tankWidget = create((Tank) notLyingObject);
                cellWidget.addItem(tankWidget);
            }
        }


        if(lyingObject == null && notLyingObject == null) {
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
        wall.addObjectListener(item);
        wall.addDamageableObjectListener(item);
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
        water.addObjectListener(item);
        return item;
    }

    public WaterWidget getWidget(Water water) {
        return _fieldWidget.water().get(water);
    }

    public HeadquartersWidget create(Headquarters headquarters) {
        if(_fieldWidget.headquarters().containsKey(headquarters)) return _fieldWidget.headquarters().get(headquarters);

        HeadquartersWidget item = new HeadquartersWidget(headquarters);
        _fieldWidget.headquarters().put(headquarters, item);
        headquarters.addObjectListener(item);
        headquarters.addDamageableObjectListener(item);
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
        tank.addObjectListener(item);
        tank.addDamageableObjectListener(item);
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
        bullet.addObjectListener(item);
        bullet.addMovingListener(item);
        return item;
    }
}
