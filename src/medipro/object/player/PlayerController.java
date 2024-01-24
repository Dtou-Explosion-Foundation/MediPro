package medipro.object.player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.manager.gamemanager.GameManagerModel;

/**
 * プレイヤーのコントローラ.
 */
public class PlayerController extends GameObjectController implements KeyListener {

    /**
     * 横方向のキーの状態. 右:-1, 左:1, 停止:0
     */
    byte keyStateX = 0;

    /**
     * プレイヤーのコントローラを生成する.
     * 
     * @param model 対象のモデル
     */
    public PlayerController(GameObjectModel model) {
        super(model);
    }

    /**
     * キーが押された時の処理. keyStateXを設定する.対象のモデルに対して、moveLeft()またはmoveRight()を呼び出す.
     * 
     * @param e キーイベント
     */
    @Override
    public void keyPressed(KeyEvent e) {
        PlayerModel playerModel = (PlayerModel) model;
        int k = e.getKeyCode();
        switch (k) {
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_A:
            playerModel.moveLeft();
            keyStateX = -1;
            break;
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_D:
            playerModel.moveRight();
            keyStateX = 1;
            break;
        case KeyEvent.VK_ESCAPE:
            switch (GameManagerModel.getPause()) {
            case 1:
                logger.info("paused");
                GameManagerModel.setPause(0);
                break;
            case 0:
                GameManagerModel.setPause(1);
                break;
            }
            break;
        }

    }

    /**
     * キーがタイプされた時の処理. 未使用
     * 
     * @param e キーイベント
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * キーが離された時の処理. keyStateXをリセットする.
     * 
     * @param e キーイベント
     */
    @Override
    public void keyReleased(KeyEvent e) {
        keyStateX = 0;
    }

    /**
     * モデルを次フレームの状態に更新する. keyStateXに応じてmoveLeft()またはmoveRight()を呼び出す. keyPressed()で既に呼び出されている場合も、再度呼び出されるので注意. その後、{@code updateMovement}, {@code updateAnimation}の順に{@code PlayerModel}を更新する.
     * 
     * @param dt 前フレームからの経過時間
     */
    @Override
    public void update(double dt) {
        PlayerModel playerModel = (PlayerModel) model;
        switch (keyStateX) {
        case -1:
            playerModel.moveLeft();
            break;

        case 1:
            playerModel.moveRight();
            break;

        default:
            break;
        }
        playerModel.update(dt);
    }

    /**
     * 自動移動処理をキューに追加する.
     * 
     * @param autoWalker 自動移動処理
     */
    public void pushAutoWalker(AutoWalker autoWalker) {
        PlayerModel playerModel = (PlayerModel) model;
        playerModel.pushAutoWalker(autoWalker);
    }
}