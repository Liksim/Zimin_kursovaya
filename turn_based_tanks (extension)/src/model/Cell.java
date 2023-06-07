package model;

import model.objects.Obstacle;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Cell {

    private HashMap<Direction, Cell> _neighbors = new HashMap<>();
    private ArrayList<Obstacle> _obstacles = new ArrayList<>();
    private Point _position;

    public Cell(Point position) {
        _position = position;
    }

    public Point position() {
        return _position;
    }

    //---------------Работа с соседними ячейками---------------
    public void setNeighbors(HashMap<Direction, Cell> neighbors) {
        _neighbors.putAll(neighbors);
    }
    public HashMap<Direction, Cell> neighbors() {
        return _neighbors;
    }
    public Cell neighborInDirection(Direction direction) {
        return _neighbors.get(direction);
    }

    //---------------Работа с препятствиями---------------

    public ArrayList<Obstacle> obstacles() {
        return _obstacles;
    }

    public void setObstacle(Obstacle obstacle) {
        _obstacles.add(obstacle);
        obstacle.setCell(this);
    }

    public boolean extractObstacle(Obstacle obstacle) {
        return _obstacles.remove(obstacle);
    }
}
