package model.objects;

import model.Cell;
import model.Field;
import model.Game;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class WaterTest {

    @Test
    void createTest() {
        Water water = new Water();
        assertNotNull(water);
        assertEquals(0, water.team());
    }

    @Test
    void takeDamage() {
        Point pos = new Point(0, 0);
        Cell cell = new Cell(pos);
        Water water = new Water();

        cell.setObstacle(water);
        water.takeDamage(3, 1);

        assertTrue(cell.obstacles().contains(water));
    }
}