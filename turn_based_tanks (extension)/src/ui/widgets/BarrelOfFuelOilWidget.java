package ui.widgets;

import model.objects.BarrelOfFuelOil;
import ui.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BarrelOfFuelOilWidget extends CellItemWidget {

    private final BarrelOfFuelOil _barrelOfFuelOil;

    public BarrelOfFuelOilWidget(BarrelOfFuelOil barrelOfFuelOil) {
        _barrelOfFuelOil = barrelOfFuelOil;
        setPreferredSize(new Dimension(60, 60));
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getBarrelOfFuelOilImageFile());
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

    private File getBarrelOfFuelOilImageFile() {
        return new File("images/barrelOfFuelOil.png");
    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(60,60);
    }
}
