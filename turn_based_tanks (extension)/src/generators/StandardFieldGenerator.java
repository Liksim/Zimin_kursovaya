package generators;

import event.TankActionListener;
import model.Cell;
import model.Direction;
import model.Field;
import event.ObstacleListener;
import model.objects.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class StandardFieldGenerator extends AbstractFieldGenerator {

    static final int FIELD_WIDTH = 10;
    static final int FIELD_HEIGHT = 10;
    static final int WALL_HEALTH = 3;
    static final int BARREL_OF_OIL_FUEL_HEALTH = 2;
    static final int BARREL_OF_OIL_FUEL_DAMAGE = 2;
    static final int HEADQUARTERS_HEALTH = 3;
    static final int TANK_HEALTH = 3;
    static final int TANK_DAMAGE = 1;
    static final int GAME_TURNS_COUNTER_BEFORE_SHOT = 2;    // >0
    static final int MAX_GAME_TURNS_BEFORE_SHOT = 2;        // >0

    @Override
    public Field generateField(ObstacleListener listener) {

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
            cells[0][j].setObstacle(new Water());
            cells[1][j].setObstacle(new Water());
            cells[3][j].setObstacle(new Water());
            cells[5][j].setObstacle(new Water());
            cells[7][j].setObstacle(new Water());
            cells[9][j].setObstacle(new Water());
        }
        cells[9][4].setObstacle(new Water());
        cells[9][5].setObstacle(new Water());

        //---------------Стены---------------

        cells[2][0].setObstacle(new Wall(WALL_HEALTH));
        cells[2][9].setObstacle(new Wall(WALL_HEALTH));
        cells[8][0].setObstacle(new Wall(WALL_HEALTH));
        cells[8][3].setObstacle(new Wall(WALL_HEALTH));
        cells[8][4].setObstacle(new Wall(WALL_HEALTH));
        cells[8][5].setObstacle(new Wall(WALL_HEALTH));
        cells[8][6].setObstacle(new Wall(WALL_HEALTH));
        cells[8][9].setObstacle(new Wall(WALL_HEALTH));

        for(int i = 4; i < 7; i += 2) {
            cells[i][0].setObstacle(new Wall(WALL_HEALTH));
            cells[i][2].setObstacle(new Wall(WALL_HEALTH));
            cells[i][3].setObstacle(new Wall(WALL_HEALTH));

            cells[i][6].setObstacle(new Wall(WALL_HEALTH));
            cells[i][7].setObstacle(new Wall(WALL_HEALTH));
            cells[i][9].setObstacle(new Wall(WALL_HEALTH));
        }
        cells[4][4].setObstacle(new Wall(WALL_HEALTH));
        cells[6][5].setObstacle(new Wall(WALL_HEALTH));

        //---------------Заросли---------------
        cells[7][3].setObstacle(new Jungle());
        cells[7][6].setObstacle(new Jungle());
        cells[8][2].setObstacle(new Jungle());
        cells[8][7].setObstacle(new Jungle());

        //---------------Бочки мазута---------------
        for(int i = 0; i < 10; i++) {
            for(int j = 1; j < 9; j += 7) {
                cells[i][j].setObstacle(new BarrelOfFuelOil(BARREL_OF_OIL_FUEL_HEALTH, BARREL_OF_OIL_FUEL_DAMAGE));
            }
        }
        cells[2][2].setObstacle(new BarrelOfFuelOil(BARREL_OF_OIL_FUEL_HEALTH, BARREL_OF_OIL_FUEL_DAMAGE));
        cells[2][3].setObstacle(new BarrelOfFuelOil(BARREL_OF_OIL_FUEL_HEALTH, BARREL_OF_OIL_FUEL_DAMAGE));
        cells[2][4].setObstacle(new BarrelOfFuelOil(BARREL_OF_OIL_FUEL_HEALTH, BARREL_OF_OIL_FUEL_DAMAGE));
        cells[2][5].setObstacle(new BarrelOfFuelOil(BARREL_OF_OIL_FUEL_HEALTH, BARREL_OF_OIL_FUEL_DAMAGE));
        cells[2][6].setObstacle(new BarrelOfFuelOil(BARREL_OF_OIL_FUEL_HEALTH, BARREL_OF_OIL_FUEL_DAMAGE));
        cells[2][7].setObstacle(new BarrelOfFuelOil(BARREL_OF_OIL_FUEL_HEALTH, BARREL_OF_OIL_FUEL_DAMAGE));

        //---------------Штабы---------------
        Headquarters firstHeadquarters = new Headquarters(HEADQUARTERS_HEALTH, 1);
        Headquarters secondHeadquarters = new Headquarters(HEADQUARTERS_HEALTH, 2);
        cells[0][7].setObstacle(firstHeadquarters);
        cells[0][2].setObstacle(secondHeadquarters);

        //---------------Танки---------------
        Tank firstTank = new Tank(TANK_HEALTH, 1, TANK_DAMAGE, GAME_TURNS_COUNTER_BEFORE_SHOT, MAX_GAME_TURNS_BEFORE_SHOT);
        Tank secondTank = new Tank(TANK_HEALTH, 2, TANK_DAMAGE, GAME_TURNS_COUNTER_BEFORE_SHOT, MAX_GAME_TURNS_BEFORE_SHOT);

        tanks.add(firstTank);
        tanks.add(secondTank);

        cells[9][2].setObstacle(firstTank);
        cells[9][7].setObstacle(secondTank);

        //---------------Добавление слушателей---------------
        for(int i = 0; i < FIELD_HEIGHT; i++) {
            for(int j = 0; j < FIELD_WIDTH; j++) {
                for(Obstacle obstacle : cells[i][j].obstacles()) {
                    obstacle.addObstacleListener(listener);
                    if(obstacle instanceof Tank) {
                        ((Tank) obstacle).addTankActionListener((TankActionListener) listener);
                    }
                }
            }
        }

        //---------------Поле---------------
        Field field = new Field(FIELD_HEIGHT, FIELD_WIDTH, cells, tanks);

        return field;
    }
}
