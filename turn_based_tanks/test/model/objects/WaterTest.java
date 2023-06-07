package model.objects;

import model.Cell;
import model.Field;
import model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaterTest {

    @Test
    void createTest() {
        Water water = new Water();
        assertNotNull(water);
        assertEquals(0, water.team());
    }
}