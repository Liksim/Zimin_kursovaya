package model.objects;

import event.*;
import model.Cell;
import model.Direction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Bullet extends Object implements Moving {
    private int _damage;

    private Direction _direction;

    public Bullet(int team, int damage, Direction direction) {
        setTeam(team);
        setDamage(damage);
        setDirection(direction);
    }

    @Override
    public boolean move(Direction direction) {
        Cell neighborCell = _cell.neighborInDirection(direction);

            if(neighborCell != null) {

                if(!neighborCell.isThereNotLyingObject()) {
                    Bullet bullet = this;
                    Timer timer = new Timer(500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Cell fromCell = _cell;
                            _cell.extractNotLyingObject();
                            neighborCell.setNotLyingObject(bullet);
                            fireBulletMoved(fromCell, neighborCell);
                            move(direction);
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                    return true;
                }

                ((DamageableObject) neighborCell.notLyingObject()).takeDamage(_damage, _team);
            }

        die();
        return false;
    }

    public int damage(){
        return _damage;
    }

    public void setDamage(int damage) {
        _damage = damage;
    }

    public Direction direction() {
        return _direction;
    }

    public void setDirection(Direction direction) {
        _direction = direction;
    }

    //---------------События---------------
    private ArrayList<MovingListener> _movingListeners = new ArrayList<>();

    public void addMovingListener(MovingListener listener) {
        _movingListeners.add(listener);
    }

    public void removeMovingListener(MovingListener listener) {
        _movingListeners.remove(listener);
    }

    private void fireBulletMoved(Cell fromCell, Cell toCell) {
        for(MovingListener listener : _movingListeners) {
            MovingEvent event = new MovingEvent(this);
            event.setFromCell(fromCell);
            event.setToCell(toCell);
            event.setObject(this);
            listener.objectMoved(event);
        }
    }
}
