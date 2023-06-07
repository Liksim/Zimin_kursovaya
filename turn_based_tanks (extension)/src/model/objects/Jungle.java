package model.objects;

public class Jungle extends Obstacle {
    @Override
    public boolean canBeInCellWith(Moving movingObstacle) {
        return true;
    }

    @Override
    public void takeDamage(int damage, int team) {

    }
}
