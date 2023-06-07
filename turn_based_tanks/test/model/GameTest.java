package model;

import event.BulletEvent;
import event.MovingEvent;
import event.ObjectEvent;
import event.TankActionEvent;
import model.objects.Bullet;
import model.objects.Tank;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void createTest() {
        Game game = new Game();
        assertNotNull(game);
        assertNull(game.field());
        assertNull(game.activeTank());
        assertFalse(game.isTankActive());
    }

    @Test
    void startGame() {
        Game game = new Game();
        game.startGame();

        assertNotNull(game.field());
        assertSame(game.field().tanks().get(0), game.activeTank());
        assertTrue(game.isTankActive());
    }

    @Test
    void objectDiedBullet() {
        Game game = new Game();
        game.startGame();

        Bullet bullet = new Bullet(1, 1, Direction.EAST);

        ObjectEvent event = new ObjectEvent(this);
        event.setObject(bullet);
        game.objectDied(event);

        assertFalse(game.isTankActive());
    }

    @Test
    void bulletFlewOut() {
        Game game = new Game();
        game.startGame();

        assertTrue(game.isTankActive());

        Bullet bullet = new Bullet(1, 1, Direction.EAST);
        BulletEvent event = new BulletEvent(this);
        event.setBullet(bullet);
        game.bulletFlewOut(event);

        assertFalse(game.isTankActive());
    }

    @Test
    void tankMoved() {
        Game game = new Game();
        game.startGame();
        for(Tank tank : game.field().tanks()) {
            while (tank.gameTurnsCounterBeforeShot() > 0) {
                tank.decrementGameTurnsCounterBeforeShot();
            }
        }

        MovingEvent event = new MovingEvent(this);
        event.setObject(game.activeTank());
        game.objectMoved(event);

        assertEquals(1, game.actionCounter());
    }

    @Test
    void tankShot() {
        Game game = new Game();
        game.startGame();
        for(Tank tank : game.field().tanks()) {
            while (tank.gameTurnsCounterBeforeShot() > 0) {
                tank.decrementGameTurnsCounterBeforeShot();
            }
        }

        TankActionEvent event = new TankActionEvent(this);
        event.setTank(game.activeTank());
        game.tankShot(event);

        assertEquals(1, game.actionCounter());
    }

    @Test
    void tankMovedAndShot() {
        Game game = new Game();
        game.startGame();
        for(Tank tank : game.field().tanks()) {
            while (tank.gameTurnsCounterBeforeShot() > 0) {
                tank.decrementGameTurnsCounterBeforeShot();
            }
        }

        MovingEvent movingEvent = new MovingEvent(this);
        movingEvent.setObject(game.activeTank());
        game.objectMoved(movingEvent);

        TankActionEvent tankActionEvent = new TankActionEvent(this);
        tankActionEvent.setTank(game.activeTank());
        game.tankShot(tankActionEvent);

        assertEquals(2, game.actionCounter());
        assertSame(game.field().tanks().get(1), game.activeTank());
    }

    @Test
    void tankSkippedGameTurn() {
        Game game = new Game();
        game.startGame();

        TankActionEvent event = new TankActionEvent(this);
        event.setTank(game.activeTank());
        game.tankSkippedGameTurn(event);

        assertEquals(2, game.actionCounter());
        assertSame(game.field().tanks().get(1), game.activeTank());
    }

    @Test
    void bothTanksSkippedGameTurn() {
        Game game = new Game();
        game.startGame();

        TankActionEvent event1 = new TankActionEvent(this);
        event1.setTank(game.activeTank());
        game.tankSkippedGameTurn(event1);

        TankActionEvent event2 = new TankActionEvent(this);
        event2.setTank(game.activeTank());
        game.tankSkippedGameTurn(event2);

        assertEquals(2, game.actionCounter());
        assertSame(game.field().tanks().get(0), game.activeTank());
    }
}