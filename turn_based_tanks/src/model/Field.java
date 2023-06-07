package model;

import model.objects.Tank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Field {
    private int _height;
    private int _width;
    private Cell[][] _cells;
    private ArrayList<Tank> _tanks;

    public Field(int height, int width, Cell[][] cells, ArrayList<Tank> tanks) {
        _height = height;
        _width = width;
        _cells = cells;
        _tanks = tanks;
    }

    public int height() {
        return _height;
    }
    public int width() {
        return _width;
    }
    public Cell[][] cells(){
        return _cells;
    }
    public ArrayList<Tank> tanks(){
        return _tanks;
    };
}
