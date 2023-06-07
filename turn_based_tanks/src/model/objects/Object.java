package model.objects;

import event.ObjectEvent;
import event.ObjectListener;
import event.TankActionListener;
import model.Cell;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class Object {

    protected Cell _cell;

    protected int _team = 0;

    public int team() {
        return _team;
    }

    public void setTeam(int team) {
        _team = team;
    }

    public Cell cell() {
        return _cell;
    }

    public void setCell(Cell cell) {
        _cell = cell;
    }

    protected void die(){
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cell cell = _cell;
                _cell.extractNotLyingObject();
                fireObjectDied(cell);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    //---------------События---------------
    protected ArrayList<ObjectListener> _objectListeners = new ArrayList<>();

    public void addObjectListener(ObjectListener listener) {
        _objectListeners.add(listener);
    }

    public void removeObjectListener(ObjectListener listener) {
        _objectListeners.remove(listener);
    }

    protected void fireObjectDied(Cell cell) {
        for (ObjectListener listener : _objectListeners) {
            ObjectEvent event = new ObjectEvent(this);
            event.setObject(this);
            event.setCell(cell);
            listener.objectDied(event);
        }
    }
}
