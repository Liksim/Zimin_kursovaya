import event.BulletEvent;
import event.GameEvent;
import event.GameListener;
import model.Cell;
import model.Game;
import ui.Tablo;
import ui.WidgetFactory;
import ui.widgets.FieldWidget;
import ui.widgets.TankWidget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GamePanel::new);
    }

    static class GamePanel extends JFrame implements GameListener {

        private Game _game;
        private WidgetFactory _widgetFactory;
        private FieldWidget _fieldWidget;
        private Tablo _tablo;

        public GamePanel() throws HeadlessException {
            setVisible(true);

            _widgetFactory = new WidgetFactory();
            _game = new Game();
            _game.addGameListener(this);
            _game.startGame();
            _fieldWidget = new FieldWidget(_game.field(), _widgetFactory);
            _game.addGameListener(_fieldWidget);
            _fieldWidget.fillField();
            _fieldWidget.tanks().get(_game.activeTank()).setActive(true);

            _tablo = new Tablo(_game.field().tanks());

            JPanel content = new JPanel();
            content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

            content.add(_fieldWidget);
            //content.add(_tablo);  // Можно раскомментировать и будет табло, но картинки начнут необъяснимо ломаться

            setContentPane(content);

            pack();
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        @Override
        public void bulletBorn(BulletEvent event) {

        }

        @Override
        public void tankActivityChanged(GameEvent event) {

        }

        @Override
        public void turnPassed(GameEvent event) {
            if(_tablo != null) {
                _tablo.updateText();
            }
        }

        @Override
        public void winnerDetermined(GameEvent event) {
            GamePanel gamePanel = this;
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (event.tank().team() == 1) {
                        JOptionPane.showMessageDialog(gamePanel, "Победила команда жёлтых!");
                    }
                    else if(event.tank().team() == 2) {
                        JOptionPane.showMessageDialog(gamePanel, "Победила команда зелёных!");
                    }
                    else {
                        JOptionPane.showMessageDialog(gamePanel, "Победила команда: " + event.tank().team() + "!");
                    }
                    for(TankWidget tankWidget : _fieldWidget.tanks().values()) {
                        tankWidget.setFocusable(false);
                    }
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
}