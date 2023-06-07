package ui.widgets;

import event.MovingEvent;
import event.MovingListener;
import model.Direction;
import model.objects.Bullet;
import ui.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ui.ImageUtils.rotateImageClockwise;

public class BulletWidget extends CellItemWidget implements MovingListener {

    private final Bullet _bullet;

    public BulletWidget(Bullet bullet) {
        _bullet = bullet;
        setPreferredSize(new Dimension(60, 60));
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getBulletImageFile());
            image = ImageUtils.resizeImage(image, 60, 60);

            if(_bullet.direction() == Direction.EAST || _bullet.direction() == Direction.WEST) {
                image = rotateImageClockwise(image, 90);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private File getBulletImageFile() {
        return new File("images/bullet.png");
    }

    @Override
    public CellWidget.Layer getLayer() {
        return CellWidget.Layer.TOP;
    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(60, 60);
    }

    //---------------События---------------

    @Override
    public void objectMoved(MovingEvent event) {
        _cellWidget.objectMoved(this, event.fromCell(), event.toCell());
        repaint();
    }
}
