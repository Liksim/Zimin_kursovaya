package model.objects;

public class Water extends Obstacle {
    @Override
    public boolean canBeInCellWith(Moving movingObstacle) {
        if(movingObstacle instanceof Tank){
            return false;
        }
        return true;
    }

    @Override
    public void takeDamage(int damage, int team) {

    }
}
