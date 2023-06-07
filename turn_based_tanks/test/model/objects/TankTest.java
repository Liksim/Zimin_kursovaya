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

class TankTest {

    @Test
    void createTest() {
        int damage = 1;
        int gameTurnsCounterBeforeShot = 2;
        int maxGameTurnsBeforeShot = 2;
        int health = 10;
        int team = 1;
        Tank tank = new Tank(health, team, damage, gameTurnsCounterBeforeShot, maxGameTurnsBeforeShot);

        assertNotNull(tank);
        assertEquals(damage, tank.damage());
        assertEquals(gameTurnsCounterBeforeShot, tank.gameTurnsCounterBeforeShot());
        assertEquals(health, tank.health());
        assertEquals(team, tank.team());
    }
    @Test
    void takeDamageNotDead() {
        Point pos = new Point(1, 1);
        Cell cell = new Cell(pos);
        Tank tank = new Tank(3,2,1,3,3);
        cell.setNotLyingObject(tank);
        int damage = 1;
        int damageTeam = 1;

        tank.takeDamage(damage, damageTeam);

        assertEquals(2, tank.health());
        assertSame(tank, cell.notLyingObject());
    }

    @Test
    void takeDamageDead() {
        Point pos = new Point(1, 1);
        Cell cell = new Cell(pos);
        Tank tank = new Tank(1,2,1,3,3);
        cell.setNotLyingObject(tank);
        int damage = 1;
        int damageTeam = 1;

        tank.takeDamage(damage, damageTeam);

        assertEquals(0, tank.health());
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
    void takeDamageFromItsTeam() {
        Point pos = new Point(1, 1);
        Cell cell = new Cell(pos);
        Tank tank = new Tank(3,1,1,3,3);
        cell.setNotLyingObject(tank);
        int damage = 1;
        int damageTeam = 1;

        tank.takeDamage(damage, damageTeam);

        assertEquals(3, tank.health());
        assertSame(tank, cell.notLyingObject());
    }

    @Test
    void move() {
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

        Tank tank = new Tank(3,2,1,3,3);
        firstCell.setNotLyingObject(tank);

        boolean result = tank.move(Direction.EAST);

        assertTrue(result);
        assertNull(firstCell.notLyingObject());
        assertSame(tank, secondCell.notLyingObject());
        assertTrue(tank.moveStatus());
    }

    @Test
    void moveToBarrier() {
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

        Tank tank = new Tank(3,2,1,3,3);
        firstCell.setNotLyingObject(tank);
        Wall wall = new Wall(3);
        secondCell.setNotLyingObject(wall);

        boolean result = tank.move(Direction.EAST);

        assertFalse(result);
        assertSame(tank, firstCell.notLyingObject());
        assertSame(wall, secondCell.notLyingObject());
        assertFalse(tank.moveStatus());
    }

    @Test
    void moveOutField() {
        Point firstPos = new Point(0, 1);
        Cell firstCell = new Cell(firstPos);

        Tank tank = new Tank(3,2,1,3,3);
        firstCell.setNotLyingObject(tank);

        boolean result = tank.move(Direction.EAST);

        assertFalse(result);
        assertSame(tank, firstCell.notLyingObject());
        assertFalse(tank.moveStatus());
    }

    @Test
    void moveMoveStatusTrue() {
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

        Tank tank = new Tank(3,2,1,3,3);
        tank.changeMoveStatus(true);
        firstCell.setNotLyingObject(tank);

        boolean result = tank.move(Direction.EAST);

        assertFalse(result);
        assertSame(tank, firstCell.notLyingObject());
        assertNull(secondCell.notLyingObject());
        assertTrue(tank.moveStatus());
    }

    @Test
    void decrementGameTurnsCounterBeforeShot() {
        Tank tank = new Tank(3,2,1,3,3);

        tank.decrementGameTurnsCounterBeforeShot();

        assertEquals(2, tank.gameTurnsCounterBeforeShot());
    }

    @Test
    void decrementGameTurnsCounterBeforeShotZero() {
        Tank tank = new Tank(3,2,1,0,3);

        tank.decrementGameTurnsCounterBeforeShot();

        assertEquals(0, tank.gameTurnsCounterBeforeShot());
    }

    @Test
    void shoot() {
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

        Tank tank = new Tank(3,2,1,0,3);
        tank.turnTurret(Direction.EAST);
        firstCell.setNotLyingObject(tank);

        boolean result = tank.shoot();

        assertTrue(result);
        assertEquals(3, tank.gameTurnsCounterBeforeShot());
        assertTrue(tank.shotStatus());
    }

    @Test
    void shootAtPointBlankRangeToObject() {
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

        Tank tank = new Tank(3,2,1,0,3);
        tank.turnTurret(Direction.EAST);
        firstCell.setNotLyingObject(tank);

        Wall wall = new Wall(3);
        secondCell.setNotLyingObject(wall);

        boolean result = tank.shoot();

        assertTrue(result);
        assertEquals(3, tank.gameTurnsCounterBeforeShot());
        assertEquals(2, wall.health());
        assertTrue(tank.shotStatus());
    }

    @Test
    void shootAtPointBlankRangeToEdgeOfField() {
        Point pos = new Point(1, 1);
        Cell firstCell = new Cell(pos);

        Tank tank = new Tank(3,2,1,0,3);
        tank.turnTurret(Direction.EAST);
        firstCell.setNotLyingObject(tank);

        boolean result = tank.shoot();

        assertTrue(result);
        assertEquals(3, tank.gameTurnsCounterBeforeShot());
        assertTrue(tank.shotStatus());
    }

    @Test
    void shootShotStatusIsTrue() {
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

        Tank tank = new Tank(3,2,1,0,3);
        tank.turnTurret(Direction.EAST);
        tank.changeShotStatus(true);
        firstCell.setNotLyingObject(tank);

        boolean result = tank.shoot();

        assertFalse(result);
        assertEquals(0, tank.gameTurnsCounterBeforeShot());
        assertTrue(tank.shotStatus());
    }

    @Test
    void shootCounterNotZero() {
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

        Tank tank = new Tank(3,2,1,2,3);
        tank.turnTurret(Direction.EAST);
        firstCell.setNotLyingObject(tank);

        boolean result = tank.shoot();

        assertFalse(result);
        assertEquals(2, tank.gameTurnsCounterBeforeShot());
        assertFalse(tank.shotStatus());
    }
}