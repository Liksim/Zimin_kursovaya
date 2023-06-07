package model.objects;

import model.Cell;
import model.Direction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BarrelOfFuelOil extends Obstacle {

    private int _damage;

    public BarrelOfFuelOil(int health, int damage) {
        setHealth(health);
        setDamage(damage);
    }

    public int damage() {
        return _damage;
    }

    public void setDamage(int damage) {
        _damage = damage;
    }

    @Override
    public boolean canBeInCellWith(Moving movingObstacle) {
        return false;
    }

    @Override
    public void takeDamage(int damage, int team) {
        _health -= damage;
        fireObjectTakenDamage(damage);

        if(!isHealthLeft()) {
            explode();
        }
    }

    private void explode() {
        ArrayList<Obstacle> obstaclesAreInExplosion = obstaclesAreInExplosion();
        die();

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Obstacle obstacle : obstaclesAreInExplosion) {
                    obstacle.takeDamage(_damage, 0);
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private ArrayList<Obstacle> obstaclesAreInExplosion() {
        ArrayList<Obstacle> obstaclesAreInExplosion = new ArrayList<>();
        Cell neighborCell;
        for(Direction direction : Direction.values()) {
            neighborCell = _cell.neighborInDirection(direction);
            if(neighborCell != null) {
                obstaclesAreInExplosion.addAll(neighborCell.obstacles());
            }
        }
        return obstaclesAreInExplosion;
    }
}
