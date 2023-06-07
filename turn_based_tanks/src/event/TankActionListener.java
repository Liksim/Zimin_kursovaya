package event;

import java.util.EventListener;

public interface TankActionListener extends MovingListener {
    void tankShot(TankActionEvent event);
    void bulletFlewOut(BulletEvent event);
    void tankSkippedGameTurn(TankActionEvent event);
    void turretTurned(TankActionEvent event);
}
