package generators;

import event.TankActionListener;
import model.Cell;
import model.Direction;
import model.Field;
import event.ObjectListener;
import model.objects.Headquarters;
import model.objects.Tank;
import model.objects.Wall;
import model.objects.Water;

import java.awt.*;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;

public class StandardFieldGenerator extends AbstractFieldGenerator {

    static final int FIELD_WIDTH = 10;
    static final int FIELD_HEIGHT = 10;
    static final int WALL_HEALTH = 3;
    static final int HEADQUARTERS_HEALTH = 3;
    static final int TANK_HEALTH = 3;
    static final int TANK_DAMAGE = 1;
    static final int GAME_TURNS_COUNTER_BEFORE_SHOT = 2;    // >0
    static final int MAX_GAME_TURNS_BEFORE_SHOT = 2;        // >0

    @Override
    public Field generateField(EventListener listener) {

        Cell[][] cells = new Cell[FIELD_HEIGHT][FIELD_WIDTH];
        ArrayList<Tank> tanks = new ArrayList<>();

        //---------------Ячейки---------------
        for(int i = 0; i < 10; i++) {       // строки
            for(int j = 0; j < 10; j++) {   // колонки
                cells[i][j] = new Cell(new Point(i, j));
            }
        }

        HashMap<Direction, Cell> neighbors = new HashMap<>();
        for(int i = 0; i < 10; i++) {       // строки
            for(int j = 0; j < 10; j++) {   // колонки

                neighbors.clear();

                if(i != 0){
                    neighbors.put(Direction.NORTH, cells[i - 1][j]);
                }
                if(j != 9) {
                    neighbors.put(Direction.EAST, cells[i][j + 1]);
                }
                if(i != 9) {
                    neighbors.put(Direction.SOUTH, cells[i + 1][j]);
                }
                if(j != 0) {
                    neighbors.put(Direction.WEST, cells[i][j - 1]);
                }

                cells[i][j].setNeighbors(neighbors);
            }
        }

        //---------------Вода---------------
        for(int j = 0; j < 10; j += 9) {
            cells[0][j].setLyingObject(new Water());
            cells[1][j].setLyingObject(new Water());
            cells[3][j].setLyingObject(new Water());
            cells[5][j].setLyingObject(new Water());
            cells[7][j].setLyingObject(new Water());
            cells[9][j].setLyingObject(new Water());
        }
        cells[9][4].setLyingObject(new Water());
        cells[9][5].setLyingObject(new Water());

        //---------------Стены---------------
        for(int i = 2; i < 9; i += 6) {
            cells[i][0].setNotLyingObject(new Wall(WALL_HEALTH));
            cells[i][1].setNotLyingObject(new Wall(WALL_HEALTH));
            cells[i][3].setNotLyingObject(new Wall(WALL_HEALTH));
            cells[i][4].setNotLyingObject(new Wall(WALL_HEALTH));
            cells[i][5].setNotLyingObject(new Wall(WALL_HEALTH));
            cells[i][6].setNotLyingObject(new Wall(WALL_HEALTH));
            cells[i][8].setNotLyingObject(new Wall(WALL_HEALTH));
            cells[i][9].setNotLyingObject(new Wall(WALL_HEALTH));
        }
        for(int i = 4; i < 7; i += 2) {
            cells[i][0].setNotLyingObject(new Wall(WALL_HEALTH));
            cells[i][1].setNotLyingObject(new Wall(WALL_HEALTH));
            cells[i][2].setNotLyingObject(new Wall(WALL_HEALTH));
            cells[i][3].setNotLyingObject(new Wall(WALL_HEALTH));

            cells[i][6].setNotLyingObject(new Wall(WALL_HEALTH));
            cells[i][7].setNotLyingObject(new Wall(WALL_HEALTH));
            cells[i][8].setNotLyingObject(new Wall(WALL_HEALTH));
            cells[i][9].setNotLyingObject(new Wall(WALL_HEALTH));
        }
        cells[4][4].setNotLyingObject(new Wall(WALL_HEALTH));
        cells[6][5].setNotLyingObject(new Wall(WALL_HEALTH));

        //---------------Штабы---------------
        Headquarters firstHeadquarters = new Headquarters(HEADQUARTERS_HEALTH, 1);
        Headquarters secondHeadquarters = new Headquarters(HEADQUARTERS_HEALTH, 2);
        cells[0][7].setNotLyingObject(firstHeadquarters);
        cells[0][2].setNotLyingObject(secondHeadquarters);
        firstHeadquarters.addObjectListener((ObjectListener) listener);
        secondHeadquarters.addObjectListener((ObjectListener) listener);

        //---------------Танки---------------
        Tank firstTank = new Tank(TANK_HEALTH, 1, TANK_DAMAGE, GAME_TURNS_COUNTER_BEFORE_SHOT, MAX_GAME_TURNS_BEFORE_SHOT);
        Tank secondTank = new Tank(TANK_HEALTH, 2, TANK_DAMAGE, GAME_TURNS_COUNTER_BEFORE_SHOT, MAX_GAME_TURNS_BEFORE_SHOT);

        tanks.add(firstTank);
        tanks.add(secondTank);

        firstTank.addTankActionListener((TankActionListener) listener);
        firstTank.addObjectListener((ObjectListener) listener);

        secondTank.addTankActionListener((TankActionListener) listener);
        secondTank.addObjectListener((ObjectListener) listener);

        cells[9][2].setNotLyingObject(firstTank);
        cells[9][7].setNotLyingObject(secondTank);

        //---------------Поле---------------
        Field field = new Field(FIELD_HEIGHT, FIELD_WIDTH, cells, tanks);

        return field;
    }
}
