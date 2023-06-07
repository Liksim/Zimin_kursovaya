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
    void extractObstacle() {
        Point pos = new Point(1,1);
        Cell cell = new Cell(pos);
        Wall wall = new Wall(3);

        cell.setObstacle(wall);

        boolean actual = cell.extractObstacle(wall);

        assertEquals(0, cell.obstacles().size());
        assertTrue(actual);
    }
}