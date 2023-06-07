package ui.widgets;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class CellItemWidget extends JPanel {
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
}
