package model;

import model.objects.Wall;
import model.objects.Water;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void createTest() {
        Point pos = new Point(1,1);
        Cell cell = new Cell(pos);

        assertNotNull(cell);
        assertEquals(1, cell.position().x);
        assertEquals(1, cell.position().y);
    }

    @Test
    void isEmptyTrue() {
        Point pos = new Point(1,1);
        Cell cell = new Cell(pos);

        assertTrue(cell.isEmpty());
    }

    @Test
    void isEmptyFalseNotLyingObject() {
        Point pos = new Point(1,1);
        Cell cell = new Cell(pos);
        Wall wall = new Wall(3);

        cell.setNotLyingObject(wall);

        assertFalse(cell.isEmpty());
    }

    @Test
    void isEmptyFalseLyingObject() {
        Point pos = new Point(1,1);
        Cell cell = new Cell(pos);
        Water water = new Water();

        cell.setLyingObject(water);

        assertFalse(cell.isEmpty());
    }

    @Test
    void isEmptyFalseFull() {
        Point pos = new Point(1,1);
        Cell cell = new Cell(pos);
        Wall wall = new Wall(3);
        Water water = new Water();

        cell.setNotLyingObject(wall);
        cell.setLyingObject(water);

        assertFalse(cell.isEmpty());
    }

    @Test
    void neighborInDirection() {
        Point pos = new Point(1,1);
        Cell cell = new Cell(pos);
        Point neighborPos = new Point(2,1);
        Cell neighborCell = new Cell(neighborPos);

        HashMap<Direction, Cell> neighbors = new HashMap<>();
        neighbors.put(Direction.EAST, neighborCell);
        cell.setNeighbors(neighbors);

        assertSame(neighborCell, cell.neighborInDirection(Direction.EAST));
    }

    @Test
    void extractNotLyingObject() {
        Point pos = new Point(1,1);
        Cell cell = new Cell(pos);
        Wall wall = new Wall(3);

        cell.setNotLyingObject(wall);

        Object actual = cell.extractNotLyingObject();

        assertNull(cell.notLyingObject());
        assertSame(wall, actual);
    }

    @Test
    void isThereNotLyingObjectTrue() {
        Point pos = new Point(1,1);
        Cell cell = new Cell(pos);
        Wall wall = new Wall(3);

        cell.setNotLyingObject(wall);

        assertTrue(cell.isThereNotLyingObject());
    }

    @Test
    void isThereNotLyingObjectFalseEmpty() {
        Point pos = new Point(1,1);
        Cell cell = new Cell(pos);

        assertFalse(cell.isThereNotLyingObject());
    }

    @Test
    void isThereNotLyingObjectFalseOnlyLyingObject() {
        Point pos = new Point(1,1);
        Cell cell = new Cell(pos);
        Water water = new Water();

        cell.setLyingObject(water);

        assertFalse(cell.isThereNotLyingObject());
    }

    @Test
    void extractLyingObject() {
        Point pos = new Point(1,1);
        Cell cell = new Cell(pos);
        Water water = new Water();

        cell.setLyingObject(water);

        Object actual = cell.extractLyingObject();

        assertNull(cell.lyingObject());
        assertSame(water, actual);
    }

    @Test
    void isThereLyingObjectTrue() {
        Point pos = new Point(1,1);
        Cell cell = new Cell(pos);
        Water water = new Water();

        cell.setLyingObject(water);

        assertTrue(cell.isThereLyingObject());
    }

    @Test
    void isThereLyingObjectFalseEmpty() {
        Point pos = new Point(1,1);
        Cell cell = new Cell(pos);

        assertFalse(cell.isThereLyingObject());
    }

    @Test
    void isThereLyingObjectFalseOnlyNotLyingObject() {
        Point pos = new Point(1,1);
        Cell cell = new Cell(pos);
        Wall wall = new Wall(3);

        cell.setNotLyingObject(wall);

        assertFalse(cell.isThereLyingObject());
    }
}