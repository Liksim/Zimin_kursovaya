package ui.widgets;

import model.objects.Wall;
import org.w3c.dom.events.Event;
import ui.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WallWidget extends CellItemWidget {

    private final Wall _wall;

    public WallWidget(Wall wall) {
        _wall = wall;
        setPreferredSize(new Dimension(60, 60));
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getWallImageFile());
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

    private File getWallImageFile() {
        return new File("images/wall.png");
    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(60,60);
    }
}
