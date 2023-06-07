package ui.widgets;

import model.Cell;
import ui.CellImage;
import ui.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CellWidget extends JLayeredPane {

    private final FieldWidget _fieldWidget;
    private static final int CELL_SIZE = 60;

    public enum Layer {
        GROUND,
        BOTTOM,
        TOP
    }
    private Map<Layer, CellItemWidget> _items = new HashMap();

    public CellWidget(FieldWidget fieldWidget) {
        _fieldWidget = fieldWidget;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setOpaque(true);
        setBackground(ImageUtils.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(), 0, 0, null);
    }

    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("images/cell.png"));
            image = ImageUtils.resizeImage(image, 60, 60);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void addItem(CellItemWidget item) {
        if(_items.size() > 3) throw new IllegalArgumentException();
        int index = -1;

       if (item.getLayer() == Layer.GROUND){
            index = 1;
            if(_items.containsKey(Layer.GROUND)) {
                remove(_items.get(Layer.GROUND));
                _items.remove(Layer.GROUND);
            }
        }
        else if (item.getLayer() == Layer.BOTTOM) {
            index = 2;
        }
        else if (item.getLayer() == Layer.TOP) {
            index = 3;
        }

        _items.put(item.getLayer(), item);
        add(item, index, index);
        item.setCellWidget(this);
        repaint();
    }

    public void removeItem(CellItemWidget item) {
        if (_items.containsValue(item)) {
            remove(item);
            _items.remove(item.getLayer());

            if (item.getLayer() == Layer.GROUND) {
                CellImage cellImage = new CellImage();
                add(cellImage, 1);
                _items.put(cellImage.getLayer(), cellImage);
            }

            repaint();
        }
    }

    //---------------Обработка сигналов---------------

    public void objectMoved(CellItemWidget item, Cell fromCell, Cell toCell) {
        _fieldWidget.cells().get(fromCell).removeItem(item);
        _fieldWidget.cells().get(toCell).addItem(item);
    }
}
