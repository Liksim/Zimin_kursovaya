package ui;

import ui.widgets.CellItemWidget;
import ui.widgets.CellWidget;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CellImage extends CellItemWidget {

    public CellImage() {
        setPreferredSize(new Dimension(60, 60));
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getCellImageFile());
            image = ImageUtils.resizeImage(image, 60, 60);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public CellWidget.Layer getLayer() {
        return CellWidget.Layer.GROUND;
    }

    private File getCellImageFile() {
        return new File("images/cell.png");
    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(60, 60);
    }
}
