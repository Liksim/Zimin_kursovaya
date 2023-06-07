package model.objects;

import model.Cell;
import model.Game;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;

class HeadquartersTest {
    @Test
    void createTest() {
        int team = 1;
        int health = 3;
        Headquarters headquarters = new Headquarters(health, team);
        assertNotNull(headquarters);
        assertEquals(headquarters.team(), team);
        assertEquals(headquarters.health(), health);
    }

    @Test
    void takeDamageNotDead() {
        Point pos = new Point(1, 1);
        Cell cell = new Cell(pos);
        Headquarters headquarters = new Headquarters(3, 2);
        cell.setObstacle(headquarters);
        int damage = 1;
        int damageTeam = 1;

        headquarters.takeDamage(damage, damageTeam);

        assertEquals(2, headquarters.health());
        assertTrue(cell.obstacles().contains(headquarters));
    }

    @Test
    void takeDamageDead() {
        Point pos = new Point(1, 1);
        Cell cell = new Cell(pos);
        Headquarters headquarters = new Headquarters(1, 2);
        cell.setObstacle(headquarters);
        int damage = 1;
        int damageTeam = 1;

        headquarters.takeDamage(damage, damageTeam);

        assertEquals(0, headquarters.health());
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assertEquals(0, cell.obstacles().size());
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Test
    void takeDamageFromItsTeam() {
        Point pos = new Point(1, 1);
        Cell cell = new Cell(pos);
        Headquarters headquarters = new Headquarters(3, 1);
        cell.setObstacle(headquarters);
        int damage = 1;
        int damageTeam = 1;

        headquarters.takeDamage(damage, damageTeam);

        assertEquals(3, headquarters.health());
        assertTrue(cell.obstacles().contains(headquarters));
    }
}