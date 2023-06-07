package model.objects;

public class Wall extends Obstacle {

    public Wall(int health) {
        setHealth(health);
    }

    @Override
    public boolean canBeInCellWith(Moving movingObstacle) {
        return false;
    }

    @Override
    public void takeDamage(int damage, int team) {
        _health -= damage;
        fireObjectTakenDamage(damage);

        if(!isHealthLeft()) {
            die();
        }
    }
}
