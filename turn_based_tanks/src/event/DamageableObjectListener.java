package event;

public interface DamageableObjectListener extends ObjectListener {
    void objectTakenDamage(DamageableObjectEvent event);
}
