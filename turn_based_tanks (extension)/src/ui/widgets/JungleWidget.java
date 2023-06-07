package ui.widgets;

import model.objects.Jungle;
import model.objects.Wall;
import ui.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JungleWidget extends CellItemWidget {
    private final Jungle _jungle;

    public JungleWidget(Jungle jungle) {
        _jungle = jungle;
        setPreferredSize(new Dimension(60, 60));
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getJungleImageFile());
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

    private File getJungleImageFile() {
        return new File("images/jungle.png");
    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(60,60);
    }
}
