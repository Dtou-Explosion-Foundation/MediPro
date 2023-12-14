package medipro.object.player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

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
     * @param models 格納するモデル
     */
    public PlayerController(GameObjectModel... models) {
        super(models);
    }

    /**
     * キーが押された時の処理. keyStateXを設定する.格納するモデルに対して、moveLeft()またはmoveRight()を呼び出す.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        switch (k) {
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_A:
            for (GameObjectModel model : models) {
                ((PlayerModel) model).moveLeft();
            }
            keyStateX = -1;
            break;
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_D:
            for (GameObjectModel model : models) {
                ((PlayerModel) model).moveRight();
            }
            keyStateX = 1;
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
     * モデルを次フレームの状態に更新する. keyStateXに応じてmoveLeft()またはmoveRight()を呼び出す.
     * keyPressed()で既に呼び出されている場合も、再度呼び出されるので注意. その後、{@code updateMovement},
     * {@code updateAnimation}の順に{@code PlayerModel}を更新する.
     * 
     * @param model 更新対象のモデル
     * @param dt    前フレームからの経過時間
     */
    @Override
    public void update(GameObjectModel model, float dt) {
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
        playerModel.updateMovement(dt);
        playerModel.updateAnimation(dt);
    }
}