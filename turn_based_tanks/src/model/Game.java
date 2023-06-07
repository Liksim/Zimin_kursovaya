package model;

import event.*;
import generators.StandardFieldGenerator;
import model.objects.Bullet;
import model.objects.Headquarters;
import model.objects.Object;
import model.objects.Tank;

import java.util.ArrayList;

public class Game implements ObjectListener, TankActionListener {

    private Field _field;
    private Tank _activeTank;
    private boolean _isTankActive = false;
    private int _actionCounter = 2;
    static final int MAX_AMOUNT_OF_ACTIONS = 2;

    public Field field() {
        return _field;
    }

    public Tank activeTank(){
        return _activeTank;
    }

    public boolean isTankActive() {
        return _isTankActive;
    }

    public int actionCounter() {
        return _actionCounter;
    }

    public void startGame() {
        StandardFieldGenerator generator = new StandardFieldGenerator();
        _field = generator.generateField(this);

        _activeTank = _field.tanks().get(0);
        changeTankActivity();

        for (Tank tank : _field.tanks()) {
            tank.decrementGameTurnsCounterBeforeShot();
        }

        fireTurnPassed(_activeTank);
    }

    private void determineWinner(Object deadHeadquartersOrTank) {
        int winTeam = -1;

        if(deadHeadquartersOrTank.team() == 1) {
            winTeam = 2;
        }
        else if(deadHeadquartersOrTank.team() == 2) {
            winTeam = 1;
        }

        for(Tank tank : _field.tanks()) {
            if(tank.team() == winTeam) {
                fireWinnerDetermined(tank);
            }
        }
    }

    // Сменить ход (передать ход другому танку)
    private void passTurn() {
        resetActionCounter();
        _activeTank.changeMoveStatus(false);
        _activeTank.changeShotStatus(false);
        _activeTank.decrementGameTurnsCounterBeforeShot();

        int indexOfNextTank = _field.tanks().indexOf(_activeTank) + 1;

        if(indexOfNextTank == _field.tanks().size()) {
            _activeTank = _field.tanks().get(0);
        }
        else {
            _activeTank = _field.tanks().get(indexOfNextTank);
        }

        fireTurnPassed(_activeTank);
    }

    private void decrementActionCounter() {
        _actionCounter--;

        if(_actionCounter == 0 || (_actionCounter == 1 && _activeTank.gameTurnsCounterBeforeShot() != 0 && _activeTank.moveStatus())) {
            passTurn();
        }
    }

    private void resetActionCounter(){
        _actionCounter = MAX_AMOUNT_OF_ACTIONS;
    }

    private void changeTankActivity() {
        _isTankActive = !_isTankActive;
        fireTankActivityChanged();
    }

    //---------------Сигналы---------------
    @Override
    public void objectDied(ObjectEvent event) {
        if(event.object() instanceof Tank || event.object() instanceof Headquarters) {
            determineWinner(event.object());
        }
        else if(event.object() instanceof Bullet) {
            changeTankActivity();
        }
    }

    @Override
    public void tankShot(TankActionEvent event) {
        decrementActionCounter();
    }

    @Override
    public void bulletFlewOut(BulletEvent event) {
        event.bullet().addObjectListener(this);
        changeTankActivity();
        fireBulletBorn(event);
    }

    @Override
    public void tankSkippedGameTurn(TankActionEvent event) {
        passTurn();
    }

    @Override
    public void turretTurned(TankActionEvent event) {

    }

    @Override
    public void objectMoved(MovingEvent event) {
        if(event.object() instanceof Tank) {
            decrementActionCounter();
        }
    }

    //---------------События---------------
    private ArrayList<GameListener> _gameListeners = new ArrayList<>();

    public void addGameListener(GameListener listener) {
        _gameListeners.add(listener);
    }

    public void removeGameListener(GameListener listener) {
        _gameListeners.remove(listener);
    }

    private void fireTurnPassed(Tank tank) {
        for(GameListener listener : _gameListeners) {
            GameEvent event = new GameEvent(this);
            event.setTank(tank);
            event.setIsActive(_isTankActive);
            listener.turnPassed(event);
        }
    }

    private void fireWinnerDetermined(Tank tank) {
        for(GameListener listener : _gameListeners) {
            GameEvent event = new GameEvent(this);
            event.setTank(tank);
            listener.winnerDetermined(event);
        }
    }

    private void fireBulletBorn(BulletEvent event) {
        for(GameListener listener : _gameListeners) {
            listener.bulletBorn(event);
        }
    }

    private void fireTankActivityChanged() {
        for(GameListener listener : _gameListeners) {
            GameEvent event = new GameEvent(this);
            event.setTank(_activeTank);
            event.setIsActive(_isTankActive);
            listener.tankActivityChanged(event);
        }
    }
}
