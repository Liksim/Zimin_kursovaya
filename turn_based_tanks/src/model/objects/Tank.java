package model.objects;

import event.*;
import model.Cell;
import model.Direction;

import java.util.ArrayList;

public class Tank extends DamageableObject implements Moving {

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

    //---------------Перемещение---------------
    @Override
    public boolean move(Direction direction) {
        turnTurret(direction);
        Cell neighborCell = _cell.neighborInDirection(direction);

        if(isAbleToMove()){
            if(neighborCell != null){
                if(neighborCell.isEmpty())
                {
                    Cell fromCell = _cell;
                    _cell.extractNotLyingObject();
                    neighborCell.setNotLyingObject(this);
                    changeMoveStatus(true);
                    fireTankMoved(fromCell, neighborCell);
                    return true;
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
                if (!neighborCell.isThereNotLyingObject()) {
                    Bullet bullet = new Bullet(_team, _damage, _turretDirection);
                    neighborCell.setNotLyingObject(bullet);
                    fireBulletFlewOut(bullet);
                    bullet.move(_turretDirection);
                }
                else {
                    ((DamageableObject) neighborCell.notLyingObject()).takeDamage(_damage, _team);
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
