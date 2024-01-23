package medipro.titlemenu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import medipro.gui.panel.IGamePanel;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.manager.gamemanager.GameManagerModel;
import medipro.world.PlayWorld;

/**
 * タイトルメニューのコントローラ.
 */
public class TitleMenuController extends GameObjectController implements KeyListener {

    /**
     * タイトルメニューのコントローラを生成する.
     * 
     * @param model 対象のモデル
     */
    public TitleMenuController(TitleMenuModel model) {
        super(model);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        TitleMenuModel titleMenuModel = (TitleMenuModel) model;
        JPanel panel = titleMenuModel.world.getPanel();
        IGamePanel gamePanel = (IGamePanel) (panel);

        switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
        case KeyEvent.VK_W:
            titleMenuModel.prevItem();
            break;
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_S:
            titleMenuModel.nextItem();
            break;
        case KeyEvent.VK_ENTER:
        case KeyEvent.VK_Z:
            switch (titleMenuModel.getSelectedItem()) {
            case 0:
                // model.world.setWorld((World)new TestWorld(model.world.getPanel()));
                titleMenuModel.setSelectedItem(0);
                GameManagerModel.setFloor(0);
                gamePanel.setWorld(new PlayWorld(panel));
                logger.info("game start");
                panel.removeKeyListener(this);
                break;
            case 1:
                logger.info("quit");
                System.exit(0);
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
