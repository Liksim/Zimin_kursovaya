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

class BarrelOfFuelOilTest {
    @Test
    void createTest() {
        int team = 0;
        int health = 3;
        int damage = 3;
        BarrelOfFuelOil barrelOfFuelOil = new BarrelOfFuelOil(health, damage);
        assertNotNull(barrelOfFuelOil);
        assertEquals(barrelOfFuelOil.team(), team);
        assertEquals(barrelOfFuelOil.health(), health);
    }

    @Test
    void takeDamageNotDead() {
        Point pos = new Point(1, 1);
        Cell cell = new Cell(pos);
        BarrelOfFuelOil barrelOfFuelOil = new BarrelOfFuelOil(3, 3);
        cell.setObstacle(barrelOfFuelOil);
        int damage = 1;
        int damageTeam = 1;

        barrelOfFuelOil.takeDamage(damage, damageTeam);

        assertEquals(2, barrelOfFuelOil.health());
        assertTrue(cell.obstacles().contains(barrelOfFuelOil));
    }

    @Test
    void takeDamageDead() {
        Point pos = new Point(1, 1);
        Cell cell = new Cell(pos);
        BarrelOfFuelOil barrelOfFuelOil = new BarrelOfFuelOil(1, 3);
        cell.setObstacle(barrelOfFuelOil);
        int damage = 1;
        int damageTeam = 1;

        barrelOfFuelOil.takeDamage(damage, damageTeam);

        assertEquals(0, barrelOfFuelOil.health());
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
    void barrelOfFuelOilDamageToEverythingAround() {
        Point centerPos = new Point(1, 1);
        Cell centerCell = new Cell(centerPos);
        Point northPos = new Point(1, 0);
        Cell northCell = new Cell(northPos);
        Point eastPos = new Point(2, 1);
        Cell eastCell = new Cell(eastPos);
        Point southPos = new Point(1, 2);
        Cell southCell = new Cell(southPos);
        Point westPos = new Point(0, 1);
        Cell westCell = new Cell(westPos);

        HashMap<Direction, Cell> centerCellNeighbors = new HashMap<>();
        centerCellNeighbors.put(Direction.NORTH, northCell);
        centerCellNeighbors.put(Direction.EAST, eastCell);
        centerCellNeighbors.put(Direction.SOUTH, southCell);
        centerCellNeighbors.put(Direction.WEST, westCell);
        centerCell.setNeighbors(centerCellNeighbors);

        HashMap<Direction, Cell> northCellNeighbors = new HashMap<>();
        northCellNeighbors.put(Direction.SOUTH, centerCell);
        northCell.setNeighbors(northCellNeighbors);

        HashMap<Direction, Cell> eastCellNeighbors = new HashMap<>();
        eastCellNeighbors.put(Direction.WEST, centerCell);
        eastCell.setNeighbors(eastCellNeighbors);

        HashMap<Direction, Cell> southCellNeighbors = new HashMap<>();
        southCellNeighbors.put(Direction.NORTH, centerCell);
        southCell.setNeighbors(southCellNeighbors);

        HashMap<Direction, Cell> westCellNeighbors = new HashMap<>();
        westCellNeighbors.put(Direction.EAST, centerCell);
        westCell.setNeighbors(westCellNeighbors);

        Wall northWall = new Wall(3);
        northCell.setObstacle(northWall);
        Wall eastWall = new Wall(3);
        eastCell.setObstacle(eastWall);
        Wall southWall = new Wall(3);
        southCell.setObstacle(southWall);
        Wall westWall = new Wall(3);
        westCell.setObstacle(westWall);

        BarrelOfFuelOil barrelOfFuelOil = new BarrelOfFuelOil(3, 3);
        centerCell.setObstacle(barrelOfFuelOil);
        int damage = 3;
        int damageTeam = 1;

        barrelOfFuelOil.takeDamage(damage, damageTeam);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assertEquals(0, centerCell.obstacles().size());
                assertEquals(0, northCell.obstacles().size());
                assertEquals(0, eastCell.obstacles().size());
                assertEquals(0, southCell.obstacles().size());
                assertEquals(0, westCell.obstacles().size());
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Test
    void chainReactionOfThreeBarrelsOfFuelOil() {
        Point northPos = new Point(1, 0);
        Cell northCell = new Cell(northPos);
        Point centerPos = new Point(1, 1);
        Cell centerCell = new Cell(centerPos);
        Point southPos = new Point(1, 2);
        Cell southCell = new Cell(southPos);

        HashMap<Direction, Cell> northCellNeighbors = new HashMap<>();
        northCellNeighbors.put(Direction.SOUTH, centerCell);
        northCell.setNeighbors(northCellNeighbors);

        HashMap<Direction, Cell> centerCellNeighbors = new HashMap<>();
        centerCellNeighbors.put(Direction.NORTH, northCell);
        centerCellNeighbors.put(Direction.SOUTH, southCell);
        centerCell.setNeighbors(centerCellNeighbors);

        HashMap<Direction, Cell> southCellNeighbors = new HashMap<>();
        southCellNeighbors.put(Direction.NORTH, centerCell);
        southCell.setNeighbors(southCellNeighbors);

        BarrelOfFuelOil northBarrelOfFuelOil = new BarrelOfFuelOil(3, 3);
        northCell.setObstacle(northBarrelOfFuelOil);
        BarrelOfFuelOil centerBarrelOfFuelOil = new BarrelOfFuelOil(3, 3);
        centerCell.setObstacle(centerBarrelOfFuelOil);
        BarrelOfFuelOil southBarrelOfFuelOil = new BarrelOfFuelOil(3, 3);
        southCell.setObstacle(southBarrelOfFuelOil);

        northBarrelOfFuelOil.takeDamage(3, 1);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assertEquals(0, northCell.obstacles().size());
                assertEquals(0, centerCell.obstacles().size());
                assertEquals(0, southCell.obstacles().size());
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}