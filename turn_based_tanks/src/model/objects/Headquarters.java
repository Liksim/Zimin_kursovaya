package model.objects;

import model.Cell;
import event.ObjectListener;

public class Headquarters extends DamageableObject{
    public Headquarters(int health, int team) {
        setHealth(health);
        setTeam(team);
    }
}
