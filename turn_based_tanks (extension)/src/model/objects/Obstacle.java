package model.objects;

import event.DamageEvent;
import event.DieEvent;
import event.ObstacleListener;
import model.Cell;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class Obstacle {

    protected Cell _cell;
    protected int _team = 0;
    protected int _health;

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

    public int health() {
        return _health;
    }

    public void setHealth(int health) {
        _health = health;
    }

    public void takeDamage(int damage, int team) {
        if(isEnemyDamage(team)) {
            _health -= damage;
            fireObjectTakenDamage(damage);

            if(!isHealthLeft()) {
                die();
            }
        }
    }

    protected boolean isEnemyDamage(int team) {
        return _team != team;
    }

    protected boolean isHealthLeft() {
        return _health > 0;
    }

    protected void die() {
        Obstacle obstacle = this;
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cell cell = _cell;
                if(_cell.extractObstacle(obstacle)) {
                    fireObjectDied(cell);
                }
                else {
                    throw new RuntimeException("В ячейке уже нет умирающего препятствия!");
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public abstract boolean canBeInCellWith(Moving movingObstacle);

    //---------------События---------------
    private ArrayList<ObstacleListener> _obstacleListeners = new ArrayList<>();

    public void addObstacleListener(ObstacleListener listener) {
        _obstacleListeners.add(listener);
    }

    public void removeObstacleListener(ObstacleListener listener) {
        _obstacleListeners.remove(listener);
    }

    protected void fireObjectDied(Cell cell) {
        for (ObstacleListener listener : _obstacleListeners) {
            DieEvent event = new DieEvent(this);
            event.setObject(this);
            event.setCell(cell);
            listener.obstacleDied(event);
        }
    }

    protected void fireObjectTakenDamage(int damage) {
        for (ObstacleListener listener : _obstacleListeners) {
            DamageEvent event = new DamageEvent(this);
            event.setObject(this);
            event.setDamage(damage);
            listener.obstacleTakenDamage(event);
        }
    }
}
