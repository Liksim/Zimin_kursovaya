package model.objects;

import model.Cell;
import model.Game;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {
    @Test
    void createTest() {
        int team = 0;
        int health = 3;
        Wall wall = new Wall(health);
        assertNotNull(wall);
        assertEquals(wall.team(), team);
        assertEquals(wall.health(), health);
    }

    @Test
    void takeDamageNotDead() {
        Point pos = new Point(1, 1);
        Cell cell = new Cell(pos);
        Wall wall = new Wall(3);
        cell.setObstacle(wall);
        int damage = 1;
        int damageTeam = 1;

        wall.takeDamage(damage, damageTeam);

        assertEquals(2, wall.health());
        assertTrue(cell.obstacles().contains(wall));
    }

    @Test
    void takeDamageDead() {
        Point pos = new Point(1, 1);
        Cell cell = new Cell(pos);
        Wall wall = new Wall(1);
        cell.setObstacle(wall);
        int damage = 1;
        int damageTeam = 1;

        wall.takeDamage(damage, damageTeam);

        assertEquals(0, wall.health());
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assertEquals(0, cell.obstacles().size());
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}