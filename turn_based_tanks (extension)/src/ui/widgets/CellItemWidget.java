package ui.widgets;

import event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public abstract class CellItemWidget extends JPanel implements ObstacleListener {
    protected CellWidget _cellWidget;

    public CellWidget cellWidget() {
        return _cellWidget;
    }

    public void setCellWidget(CellWidget cellWidget) {
        _cellWidget = cellWidget;
    }

    public CellItemWidget() {
        setBounds(0, 0, 60, 60);
        setOpaque(false);
    }

    protected abstract BufferedImage getImage();

    public abstract CellWidget.Layer getLayer();

    protected abstract Dimension getDimension();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(), 0, 0, null);
    }

    @Override
    public void obstacleTakenDamage(DamageEvent event) {
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
    public void obstacleDied(DieEvent event) {
        _cellWidget.removeItem(this);
        System.out.println("Object died");
    }
}
