package medipro.result;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import medipro.gui.panel.GamePanel;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.manager.gamemanager.GameManagerController;
import medipro.world.PlayWorld;
import medipro.world.TitleMenuWorld;

/**
 * リザルトのコントローラ.
 */
public class ResultController extends GameObjectController implements KeyListener {

    /**
     * リザルトのコントローラを生成する.
     * 
     * @param model 対象のモデル
     */
    public ResultController(ResultModel model) {
        super(model);
    }

    /**
     * キーが押された時の処理. 対象のモデルに対してprevItem()やnextItem()を呼び出す. 対象のパネルに対してsetWorld()を呼び出す.
     * 
     * @param e キーイベント
     */
    @Override
    public void keyPressed(KeyEvent e) {
        ResultModel resultModel = (ResultModel) model;
        GamePanel gamePanel = resultModel.getWorld().getPanel();
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
                GameManagerController.resetFloor();
                gamePanel.setWorld(new PlayWorld(gamePanel));
                logger.info("You selected: " + resultModel.getSelectedItem());
                break;
            case 1:
                resultModel.setSelectedItem(0);
                gamePanel.setWorld(new TitleMenuWorld(gamePanel));
                logger.info("You selected: " + resultModel.getSelectedItem());
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
