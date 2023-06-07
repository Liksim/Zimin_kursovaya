package model.objects;

import event.DamageableObjectEvent;
import event.DamageableObjectListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class DamageableObject extends Object {

    protected int _health;

    public int health() {
        return _health;
    }

    public void setHealth(int health) {
        _health = health;
    }

    public void takeDamage(int damage, int team) {
        if(isEnemyDamage(team)) {
            _health -= damage;
            fireObjectTakenDamage(damage);

            if(!isHealthLeft()) {
                die();
            }
        }
    }

    protected boolean isEnemyDamage(int team) {
        return _team != team;
    }

    protected boolean isHealthLeft() {
        return _health > 0;
    }

    //---------------События---------------
    protected ArrayList<DamageableObjectListener> _damageableObjectListeners = new ArrayList<>();

    public void addDamageableObjectListener(DamageableObjectListener listener) {
        _damageableObjectListeners.add(listener);
    }

    public void removeDamageableObjectListener(DamageableObjectListener listener) {
        _damageableObjectListeners.remove(listener);
    }

    protected void fireObjectTakenDamage(int damage) {
        for (DamageableObjectListener listener : _damageableObjectListeners) {
            DamageableObjectEvent event = new DamageableObjectEvent(this);
            event.setObject(this);
            event.setDamage(damage);
            listener.objectTakenDamage(event);
        }
    }
}
