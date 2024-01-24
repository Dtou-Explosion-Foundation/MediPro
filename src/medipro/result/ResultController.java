package medipro.result;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import medipro.gui.panel.IGamePanel;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.manager.gamemanager.GameManagerController;
import medipro.world.PlayWorld;
import medipro.world.TitleMenuWorld;

public class ResultController extends GameObjectController implements KeyListener {

    public ResultController(ResultModel model) {
        super(model);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        ResultModel resultModel = (ResultModel) model;
        JPanel panel = resultModel.world.getPanel();
        IGamePanel gamePanel = (IGamePanel) (panel);
        KeyListener[] allKeyListeners = panel.getKeyListeners();
        switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_A:
            resultModel.prevItem();
            break;
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_D:
            resultModel.nextItem();
            break;
        case KeyEvent.VK_ENTER:
        case KeyEvent.VK_Z:
            switch (resultModel.getSelectedItem()) {
            case 0:
                resultModel.setSelectedItem(0);
                for (KeyListener keyListener : allKeyListeners) {
                    panel.removeKeyListener(keyListener);
                }
                GameManagerController.resetFloor();
                gamePanel.setWorld(new PlayWorld(panel));
                logger.info("You selected: " + resultModel.getSelectedItem());
                panel.removeKeyListener(this);
                break;
            case 1:
                resultModel.setSelectedItem(0);
                for (KeyListener keyListener : allKeyListeners) {
                    panel.removeKeyListener(keyListener);
                }
                gamePanel.setWorld(new TitleMenuWorld(panel));
                logger.info("You selected: " + resultModel.getSelectedItem());
                panel.removeKeyListener(this);
                break;
            }
            break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void update(double dt) {

    }
}
