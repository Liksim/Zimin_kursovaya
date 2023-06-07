package ui.widgets;

import event.*;
import model.Direction;
import model.objects.Tank;
import ui.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ui.ImageUtils.rotateImageClockwise;

public class TankWidget extends CellItemWidget implements TankActionListener, KeyListener {

    private final Tank _tank;

    private boolean _isActive;

    public TankWidget(Tank tank) {
        _tank = tank;
        setPreferredSize(new Dimension(60, 60));
        addKeyListener(this);
    }

    public boolean isActive() {
        return _isActive;
    }
    public void setActive(boolean state) {
        _isActive = state;
        setFocusable(state);
        requestFocus();
        repaint();
    }

    //---------------Отрисовка---------------

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getTankImageFile());
            image = ImageUtils.resizeImage(image, 60, 60);

            if(_tank.turretDirection() == Direction.EAST) {
                image = rotateImageClockwise(image, 90);
            }
            else if(_tank.turretDirection() == Direction.SOUTH) {
                image = rotateImageClockwise(image, 180);
            }
            else if(_tank.turretDirection() == Direction.WEST) {
                image = rotateImageClockwise(image, 270);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private File getTankImageFile() {
        File file = null;

        if(_tank.team() == 1) {
            if(_isActive){
                file = new File("images/active_tankY.png");
            }
            else {
                file = new File("images/tankY.png");
            }
        }
        else if(_tank.team() == 2) {
            if(_isActive){
                file = new File("images/active_tankG.png");
            }
            else {
                file = new File("images/tankG.png");
            }
        }

        return file;
    }

    @Override
    public CellWidget.Layer getLayer() {
        return CellWidget.Layer.BOTTOM;
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

    @Override
    public void tankShot(TankActionEvent event) {

    }

    @Override
    public void bulletFlewOut(BulletEvent event) {

    }

    @Override
    public void tankSkippedGameTurn(TankActionEvent event) {

    }

    @Override
    public void turretTurned(TankActionEvent event) {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        System.out.println("PRESSED");

        turnTurretAction(keyCode);
        moveAction(keyCode);
        skipGameTurnAction(keyCode);
        shootAction(keyCode);

        requestFocus();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void turnTurretAction(int keyCode) {
        if(keyCode == KeyEvent.VK_W) {
            System.out.println("Tank " + _tank.team() + " turn to " + Direction.NORTH);
            _tank.turnTurret(Direction.NORTH);
        }
        else if(keyCode == KeyEvent.VK_D) {
            System.out.println("Tank " + _tank.team() + " turn to " + Direction.EAST);
            _tank.turnTurret(Direction.EAST);
        }
        else if(keyCode == KeyEvent.VK_S) {
            System.out.println("Tank " + _tank.team() + " turn to " + Direction.SOUTH);
            _tank.turnTurret(Direction.SOUTH);
        }
        else if(keyCode == KeyEvent.VK_A) {
            System.out.println("Tank " + _tank.team() + " turn to " + Direction.WEST);
            _tank.turnTurret(Direction.WEST);
        }
    }

    private void moveAction(int keyCode) {
        if(keyCode == KeyEvent.VK_UP) {
            System.out.println("Tank " + _tank.team() + " go to " + Direction.NORTH);
            _tank.move(Direction.NORTH);
        }
        else if(keyCode == KeyEvent.VK_RIGHT) {
            System.out.println("Tank " + _tank.team() + " go to " + Direction.EAST);
            _tank.move(Direction.EAST);
        }
        else if(keyCode == KeyEvent.VK_DOWN) {
            System.out.println("Tank " + _tank.team() + " go to " + Direction.SOUTH);
            _tank.move(Direction.SOUTH);
        }
        else if(keyCode == KeyEvent.VK_LEFT) {
            System.out.println("Tank " + _tank.team() + " go to " + Direction.WEST);
            _tank.move(Direction.WEST);
        }
    }

    private void skipGameTurnAction(int keyCode) {
        if(keyCode == KeyEvent.VK_R) {
            System.out.println("Tank " + _tank.team() + " skip game turn");
            _tank.skipGameTurn();
        }
    }

    private void shootAction(int keyCode) {
        if(keyCode == KeyEvent.VK_SPACE) {
            System.out.println("Tank " + _tank.team() + " shoot");
            _tank.shoot();
        }
    }
}
