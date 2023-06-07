package ui.widgets;

import event.ObjectEvent;
import event.ObjectListener;
import model.objects.Water;
import ui.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WaterWidget extends CellItemWidget implements ObjectListener {

    private final Water _water;

    public WaterWidget(Water water) {
        _water = water;
        setPreferredSize(new Dimension(60, 60));
    }
    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getWaterImageFile());
            image = ImageUtils.resizeImage(image, 60, 60);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public CellWidget.Layer getLayer() {
        return CellWidget.Layer.BOTTOM;
    }

    private File getWaterImageFile() {
        return new File("images/water.png");
    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(60, 60);
    }

    //---------------События---------------

    @Override
    public void objectDied(ObjectEvent event) {
        _cellWidget.removeItem(this);
        System.out.println("Water died");
    }
}
