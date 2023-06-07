package ui.widgets;

import model.objects.Headquarters;
import ui.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HeadquartersWidget extends DamageableCellItemWidget {

    private final Headquarters _headquarters;

    public HeadquartersWidget(Headquarters headquarters) {
        _headquarters = headquarters;
        setPreferredSize(new Dimension(60, 60));
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getHeadquartersImageFile());
            image = ImageUtils.resizeImage(image, 60, 60);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private File getHeadquartersImageFile() {
        File file = null;
        if(_headquarters.team() == 1) {
            file = new File("images/headquartersY.png");
        }
        else if(_headquarters.team() == 2) {
            file = new File("images/headquartersG.png");
        }
        return file;
    }

    @Override
    public CellWidget.Layer getLayer() {
        return CellWidget.Layer.BOTTOM;
    }



    @Override
    protected Dimension getDimension() {
        return new Dimension(60,60);
    }
}
