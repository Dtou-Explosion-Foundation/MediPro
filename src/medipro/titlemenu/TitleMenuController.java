package medipro.titlemenu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import medipro.gui.panel.GamePanel;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.manager.gamemanager.GameManagerController;
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
        GamePanel gamePanel = titleMenuModel.getWorld().getPanel();

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
                GameManagerController.resetFloor();
                gamePanel.setWorld(new PlayWorld(gamePanel));
                logger.info("game start");
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

    @Override
    public void dispose() {
        this.model.getWorld().getPanel().removeKeyListener(this);
    }
}
