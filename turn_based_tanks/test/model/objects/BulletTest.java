package model.objects;

import model.Cell;
import model.Direction;
import model.Game;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class BulletTest {
    @Test
    void createTest() {
        int team = 1;
        int damage = 1;
        Bullet bullet = new Bullet(team, damage, Direction.EAST);

        assertNotNull(bullet);
        assertEquals(bullet.team(), team);
        assertEquals(bullet.damage(), damage);
    }
    @Test
    void moveOneCellToEdgeOfField() {
        Point firstPos = new Point(0, 1);
        Point secondPos = new Point(1, 1);
        Cell firstCell = new Cell(firstPos);
        Cell secondCell = new Cell(secondPos);
        HashMap<Direction, Cell> firstCellNeighbors = new HashMap<>();
        firstCellNeighbors.put(Direction.EAST, secondCell);
        firstCell.setNeighbors(firstCellNeighbors);
        HashMap<Direction, Cell> secondCellNeighbors = new HashMap<>();
        secondCellNeighbors.put(Direction.WEST, firstCell);
        secondCell.setNeighbors(secondCellNeighbors);

        Bullet bullet = new Bullet(1, 1, Direction.EAST);
        firstCell.setNotLyingObject(bullet);
        bullet.move(Direction.EAST);

        Timer timer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assertNull(firstCell.notLyingObject());
            }
        });
        timer.setRepeats(false);
        timer.start();

        assertNull(secondCell.notLyingObject());
    }

    @Test
    void moveAtPointBlankRangeToEdgeOfField() {
        Point pos = new Point(0, 1);
        Cell cell = new Cell(pos);

        Bullet bullet = new Bullet(1, 1, Direction.EAST);
        cell.setNotLyingObject(bullet);
        bullet.move(Direction.EAST);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assertNull(cell.notLyingObject());
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Test
    void moveTreeCellToEdgeOfField() {
        Point firstPos = new Point(0, 1);
        Point secondPos = new Point(1, 1);
        Point thirdPos = new Point(2, 1);
        Point fourthPos = new Point(3, 1);
        Cell firstCell = new Cell(firstPos);
        Cell secondCell = new Cell(secondPos);
        Cell thirdCell = new Cell(thirdPos);
        Cell fourthCell = new Cell(fourthPos);
        HashMap<Direction, Cell> firstCellNeighbors = new HashMap<>();
        firstCellNeighbors.put(Direction.EAST, secondCell);
        firstCell.setNeighbors(firstCellNeighbors);
        HashMap<Direction, Cell> secondCellNeighbors = new HashMap<>();
        secondCellNeighbors.put(Direction.WEST, firstCell);
        secondCellNeighbors.put(Direction.EAST, thirdCell);
        secondCell.setNeighbors(secondCellNeighbors);
        HashMap<Direction, Cell> thirdCellNeighbors = new HashMap<>();
        thirdCellNeighbors.put(Direction.WEST, secondCell);
        thirdCellNeighbors.put(Direction.EAST, fourthCell);
        thirdCell.setNeighbors(thirdCellNeighbors);
        HashMap<Direction, Cell> fourthCellNeighbors = new HashMap<>();
        fourthCellNeighbors.put(Direction.WEST, thirdCell);
        fourthCell.setNeighbors(fourthCellNeighbors);

        Bullet bullet = new Bullet(1, 1, Direction.EAST);
        firstCell.setNotLyingObject(bullet);
        bullet.move(Direction.EAST);

        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assertNull(firstCell.notLyingObject());
            }
        });
        timer.setRepeats(false);
        timer.start();

        assertNull(secondCell.notLyingObject());
        assertNull(thirdCell.notLyingObject());
        assertNull(fourthCell.notLyingObject());
    }

    @Test
    void moveOneCellToObject() {
        Point firstPos = new Point(0, 1);
        Point secondPos = new Point(1, 1);
        Cell firstCell = new Cell(firstPos);
        Cell secondCell = new Cell(secondPos);
        HashMap<Direction, Cell> firstCellNeighbors = new HashMap<>();
        firstCellNeighbors.put(Direction.EAST, secondCell);
        firstCell.setNeighbors(firstCellNeighbors);
        HashMap<Direction, Cell> secondCellNeighbors = new HashMap<>();
        secondCellNeighbors.put(Direction.WEST, firstCell);
        secondCell.setNeighbors(secondCellNeighbors);

        Bullet bullet = new Bullet(1, 1, Direction.EAST);
        firstCell.setNotLyingObject(bullet);
        Wall wall = new Wall(3); // здоровье = 3
        secondCell.setNotLyingObject(wall);

        bullet.move(Direction.EAST);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assertNull(firstCell.notLyingObject());
            }
        });
        timer.setRepeats(false);
        timer.start();

        assertNotNull(secondCell.notLyingObject());
        assertEquals(2, wall.health());
    }
}