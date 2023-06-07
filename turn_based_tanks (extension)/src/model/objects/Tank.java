package model.objects;

import event.*;
import model.Cell;
import model.Direction;

import java.util.ArrayList;

public class Tank extends Obstacle implements Moving {

    private int _damage;
    private Direction _turretDirection = Direction.NORTH;
    private int _gameTurnsCounterBeforeShot;
    private int _maxGameTurnsBeforeShot;
    private boolean _moveStatus = false;
    private boolean _shotStatus = false;

    public Tank(int health, int team, int damage, int gameTurnsCounterBeforeShot, int maxGameTurnsBeforeShot) {
        setHealth(health);
        setTeam(team);
        setDamage(damage);
        _gameTurnsCounterBeforeShot = gameTurnsCounterBeforeShot;
        _maxGameTurnsBeforeShot = maxGameTurnsBeforeShot;
    }

    @Override
    public boolean canBeInCellWith(Moving movingObstacle) {
        if(movingObstacle instanceof Bullet) {
            for (Obstacle obstacle : cell().obstacles()) {
                if (obstacle instanceof Jungle) {
                    return true;
                }
            }
        }

        return false;
    }

    //---------------Перемещение---------------
    @Override
    public boolean move(Direction direction) {
        turnTurret(direction);
        Cell neighborCell = _cell.neighborInDirection(direction);

        if(isAbleToMove()) {
            if(neighborCell != null) {
                ArrayList<Obstacle> obstacles = neighborCell.obstacles();
                boolean canMove = true;

                for(Obstacle obstacle : obstacles) {
                    canMove = canMove && obstacle.canBeInCellWith(this);
                }

                if(canMove) {
                    Cell fromCell = _cell;

                    if(_cell.extractObstacle(this)) {
                        neighborCell.setObstacle(this);
                        changeMoveStatus(true);
                        fireTankMoved(fromCell, neighborCell);
                        return true;
                    }
                    else {
                        throw new RuntimeException("В ячейке уже нет снаряда!");
                    }
                }
            }
        }

        return false;
    }

    public void changeMoveStatus(boolean status) {
        _moveStatus = status;
    }

    public boolean moveStatus(){
        return _moveStatus;
    }

    private boolean isAbleToMove() {
        return !_moveStatus;
    }

    //---------------Выстрел---------------
    public int damage(){
        return _damage;
    }

    public void setDamage(int damage) {
        _damage = damage;
    }

    public void decrementGameTurnsCounterBeforeShot() {
        if(_gameTurnsCounterBeforeShot > 0) {
            _gameTurnsCounterBeforeShot--;
        }
    }

    private void resetGameTurnsCounterBeforeShot() {
        _gameTurnsCounterBeforeShot = _maxGameTurnsBeforeShot;
    }

    public int gameTurnsCounterBeforeShot() {
        return _gameTurnsCounterBeforeShot;
    }

    public void turnTurret(Direction direction) {
        _turretDirection = direction;
        fireTurretTurned();
    }

    public Direction turretDirection(){
        return _turretDirection;
    }

    public boolean shoot(){
        if(isAbleToShoot()) {
            Cell neighborCell = _cell.neighborInDirection(turretDirection());

            if(neighborCell != null) {
                Bullet bullet = new Bullet(_team, _damage, _turretDirection);
                ArrayList<Obstacle> obstacles = neighborCell.obstacles();
                boolean canSetBullet = true;
                ArrayList<Obstacle> damageableObstacles = new ArrayList<>();

                for(Obstacle obstacle : obstacles) {
                    if(!obstacle.canBeInCellWith(bullet)) {   // Если препятствие может находиться в одной ячейке со снарядом
                        canSetBullet = false;
                        damageableObstacles.add(obstacle);
                    }
                }

                if (canSetBullet) {
                    neighborCell.setObstacle(bullet);
                    fireBulletFlewOut(bullet);
                    bullet.move(_turretDirection);
                }
                else {
                    for(Obstacle damageableObstacle : damageableObstacles) {
                        damageableObstacle.takeDamage(_damage, _team);
                    }
                }
            }
            resetGameTurnsCounterBeforeShot();
            changeShotStatus(true);
            fireTankShot();
            return true;
            }

        return false;
    }

    public void changeShotStatus(boolean status) {
        _shotStatus = status;
    }

    public boolean shotStatus(){
        return _shotStatus;
    }

    private boolean isAbleToShoot() {
        return !_shotStatus && _gameTurnsCounterBeforeShot == 0;
    }

    //---------------Пропуск хода---------------
    public void skipGameTurn() {
        fireTankSkippedGameTurn();
    }

    //---------------События---------------
    private ArrayList<TankActionListener> _tankActionListeners = new ArrayList<>();

    public void addTankActionListener(TankActionListener listener) {
        _tankActionListeners.add(listener);
    }

    public void removeTankActionListener(TankActionListener listener) {
        _tankActionListeners.remove(listener);
    }

    private void fireTankMoved(Cell fromCell, Cell toCell) {
        for (TankActionListener listener : _tankActionListeners) {
            MovingEvent event = new MovingEvent(this);
            event.setObject(this);
            event.setFromCell(fromCell);
            event.setToCell(toCell);
            listener.objectMoved(event);
        }
    }

    private void fireBulletFlewOut(Bullet bullet) {
        for(TankActionListener listener : _tankActionListeners) {
            BulletEvent event = new BulletEvent(this);
            event.setBullet(bullet);
            listener.bulletFlewOut(event);
        }
    }

    private void fireTankShot() {
        for(TankActionListener listener : _tankActionListeners) {
            TankActionEvent event = new TankActionEvent(this);
            event.setTank(this);
            listener.tankShot(event);
        }
    }

    private void fireTankSkippedGameTurn() {
        for(TankActionListener listener : _tankActionListeners) {
            TankActionEvent event = new TankActionEvent(this);
            event.setTank(this);
            listener.tankSkippedGameTurn(event);
        }
    }

    private void fireTurretTurned() {
        for(TankActionListener listener : _tankActionListeners) {
            TankActionEvent event = new TankActionEvent(this);
            event.setTank(this);
            listener.turretTurned(event);
        }
    }
}
