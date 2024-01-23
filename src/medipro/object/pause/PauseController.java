package medipro.object.pause;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import medipro.gui.panel.IGamePanel;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.manager.gamemanager.GameManagerModel;
import medipro.world.TitleMenuWorld;
import medipro.world.PlayWorld;

public class PauseController extends GameObjectController implements KeyListener {
    public PauseController(PauseModel model){
        super(model);
    }

    @Override
    public void keyPressed(KeyEvent e){
        PauseModel pauseModel = (PauseModel) model;
        JPanel panel = pauseModel.world.getPanel();
        IGamePanel gamePanel = (IGamePanel) (panel);
        KeyListener[] allKeyListeners = panel.getKeyListeners();
        if(GameManagerModel.getPause()==0){
            switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                pauseModel.prevItem();
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                pauseModel.nextItem();
                break;
            case KeyEvent.VK_ESCAPE:
                pauseModel.setSelectedItem(0);
                break;
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_Z:
                switch(pauseModel.getSelectedItem()){
                case 0:
                    GameManagerModel.setPause(1);
                    logger.info("Unpause");
                    break;
                case 1:
                    pauseModel.setSelectedItem(0);
                    GameManagerModel.setFloor(0);
                    GameManagerModel.setPause(1);
                    for (KeyListener keyListener : allKeyListeners) {
                        panel.removeKeyListener(keyListener);
                    }
                    gamePanel.setWorld(new PlayWorld(panel));
                    logger.info("You selected: " + pauseModel.getSelectedItem());
                    break;
                case 2:
                    pauseModel.setSelectedItem(0);
                    GameManagerModel.setPause(1);
                    for (KeyListener keyListener : allKeyListeners) {
                        panel.removeKeyListener(keyListener);
                    }
                    gamePanel.setWorld(new TitleMenuWorld(panel));
                    logger.info("You selected: " + pauseModel.getSelectedItem());
                    break;
                }
                break;
            }
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
