package model.objects;

import model.Cell;
import model.Direction;
import model.Field;
import model.Game;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class JungleTest {

    @Test
    void createTest() {
        Jungle jungle = new Jungle();
        assertNotNull(jungle);
        assertEquals(0, jungle.team());
    }

    @Test
    void takeDamage() {
        Point pos = new Point(0, 0);
        Cell cell = new Cell(pos);
        Jungle jungle = new Jungle();

        cell.setObstacle(jungle);
        jungle.takeDamage(3, 1);

        assertTrue(cell.obstacles().contains(jungle));
    }

    @Test
    void bulletFlewPastTankInJungle() {
        Point firstPos = new Point(0, 0);
        Point secondPos = new Point(1, 0);
        Cell firstCell = new Cell(firstPos);
        Cell secondCell = new Cell(secondPos);
        HashMap<Direction, Cell> firstCellNeighbors = new HashMap<>();
        firstCellNeighbors.put(Direction.EAST, secondCell);
        HashMap<Direction, Cell> secondCellNeighbors = new HashMap<>();
        secondCellNeighbors.put(Direction.WEST, firstCell);
        firstCell.setNeighbors(firstCellNeighbors);
        secondCell.setNeighbors(secondCellNeighbors);
        Bullet bullet = new Bullet(1, 3, Direction.EAST);
        firstCell.setObstacle(bullet);
        Jungle jungle = new Jungle();
        secondCell.setObstacle(jungle);
        Tank tank = new Tank(3, 2, 3, 2, 2);
        secondCell.setObstacle(tank);

        bullet.move(Direction.EAST);

        assertEquals(0, firstCell.obstacles().size());
        assertEquals(2, secondCell.obstacles().size());
        assertTrue(secondCell.obstacles().contains(jungle));
        assertTrue(secondCell.obstacles().contains(tank));
    }
}