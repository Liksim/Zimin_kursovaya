package model.objects;

import model.Cell;
import event.ObjectListener;

public class Wall extends DamageableObject{
    public Wall(int health) {
        setHealth(health);
    }
}
