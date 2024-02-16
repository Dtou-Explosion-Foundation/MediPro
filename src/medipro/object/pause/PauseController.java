package medipro.object.pause;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import medipro.gui.panel.GamePanel;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.manager.gamemanager.GameManagerController;
import medipro.object.manager.gamemanager.GameManagerModel;
import medipro.world.PlayWorld;
import medipro.world.TitleMenuWorld;

public class PauseController extends GameObjectController implements KeyListener {
    public PauseController(PauseModel model) {
        super(model);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        PauseModel pauseModel = (PauseModel) model;
        GamePanel gamePanel = pauseModel.world.getPanel();
        if (!GameManagerModel.isPause()) {
            switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                logger.info("paused");
                GameManagerModel.Pause();
            }
        } else if (GameManagerModel.isPause()) {
            switch (e.getKeyCode()) {
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
                GameManagerModel.unPause();
                break;
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_Z:
                switch (pauseModel.getSelectedItem()) {
                case 0:
                    GameManagerModel.unPause();
                    logger.info("Unpause");
                    break;
                case 1:
                    pauseModel.setSelectedItem(0);
                    GameManagerController.resetFloor();
                    GameManagerModel.unPause();
                    gamePanel.setWorld(new PlayWorld(gamePanel));
                    logger.info("You selected: " + pauseModel.getSelectedItem());
                    break;
                case 2:
                    pauseModel.setSelectedItem(0);
                    GameManagerModel.unPause();
                    gamePanel.setWorld(new TitleMenuWorld(gamePanel));
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

    @Override
    public void dispose() {
        this.model.world.getPanel().removeKeyListener(this);
    }
}
