package model.objects;

public class Headquarters extends Obstacle{
    public Headquarters(int health, int team) {
        setHealth(health);
        setTeam(team);
    }

    @Override
    public boolean canBeInCellWith(Moving movingObstacle) {
        return false;
    }
}
