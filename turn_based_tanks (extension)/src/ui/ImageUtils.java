package ui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageUtils {
    public static BufferedImage resizeImage(BufferedImage img, Integer width, Integer height) {
        Image tmpImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(tmpImg, 0, 0, null);
        g.dispose();

        return bufferedImage;
    }

    public static final Color BACKGROUND_COLOR = Color.decode("#9AA6AD");

    public static BufferedImage rotateImageClockwise(BufferedImage img, int degrees) {
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        AffineTransform transform = new AffineTransform();

        transform.translate(height / 2.0, width / 2.0);
        transform.rotate(Math.toRadians(degrees));
        transform.translate(-width / 2.0, -height / 2.0);

        Graphics2D g = rotatedImage.createGraphics();
        g.drawImage(img, transform, null);
        g.dispose();

        return rotatedImage;
    }
}
