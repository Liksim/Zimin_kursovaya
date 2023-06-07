package ui;

import model.objects.Tank;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class Tablo extends JPanel {

    private ArrayList<Tank> _tanks;

    private JLabel _label;

    public Tablo(ArrayList<Tank> tanks) {
        _tanks = tanks;
        _label = new JLabel();
        _label.setFont(new Font("Roboto", 1, 16));
        setBackground(Color.decode("#ADD2E8"));
        setBorder(new LineBorder(Color.BLACK));
        updateText();
        add(_label);
    }

    public void updateText() {
        _label.setText(
                "Жёлтый: ходов до выстрела - " + _tanks.get(0).gameTurnsCounterBeforeShot() +
                "    " +
                "Зелёный: ходов до выстрела - " + _tanks.get(1).gameTurnsCounterBeforeShot()
        );
    }
}
