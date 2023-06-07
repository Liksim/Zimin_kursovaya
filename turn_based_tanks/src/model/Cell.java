package model;

import model.objects.LyingObject;
import model.objects.Object;

import java.awt.*;
import java.util.HashMap;

public class Cell {

    private HashMap<Direction, Cell> _neighbors = new HashMap<>();
    private Object _notLyingObject;
    private LyingObject _lyingObject;
    private Point _position;

    public Cell(Point position) {
        _position = position;
    }

    public Point position() {
        return _position;
    }
    public boolean isEmpty(){
        return !isThereNotLyingObject() && !isThereLyingObject();
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

    //---------------Работа со слоем НЕ лежащих объектов---------------
    public Object notLyingObject(){
        return _notLyingObject;
    }
    public void setNotLyingObject(Object object){
        _notLyingObject = object;
        object.setCell(this);
    }
    public Object extractNotLyingObject(){
        _notLyingObject.setCell(null);
        Object object = _notLyingObject;
        _notLyingObject = null;
        return object;
    }
    public boolean isThereNotLyingObject(){
        return _notLyingObject != null;
    }

    //---------------Работа со слоем лежащих объектов---------------
    public LyingObject lyingObject(){
        return _lyingObject;
    }
    public void setLyingObject(LyingObject lyingObject){
        _lyingObject = lyingObject;
        lyingObject.setCell(this);
    }
    public LyingObject extractLyingObject(){
        _lyingObject.setCell(null);
        LyingObject lyingObject = _lyingObject;
        _lyingObject = null;
        return lyingObject;
    }
    public boolean isThereLyingObject(){
        return _lyingObject != null;
    }
}
