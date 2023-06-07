package ui.widgets;

import event.DamageableObjectEvent;
import event.DamageableObjectListener;
import event.ObjectEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class DamageableCellItemWidget extends CellItemWidget implements DamageableObjectListener {

    //---------------События---------------

    @Override
    public void objectTakenDamage(DamageableObjectEvent event) {
        DamageWidget damageWidget = new DamageWidget();
        _cellWidget.addItem(damageWidget);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _cellWidget.removeItem(damageWidget);

            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void objectDied(ObjectEvent event) {
        _cellWidget.removeItem(this);
        System.out.println("Object died");
    }
}
