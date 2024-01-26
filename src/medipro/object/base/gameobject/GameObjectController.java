
package medipro.object.base.gameobject;

import java.util.logging.Logger;

/**
 * ゲームオブジェクトのコントローラクラス. 複数のモデルを保持する. フレームの更新の通知を受け取り,モデルを更新する. また、キー入力などのイベントを受け取り,モデルを更新する.
 */
public abstract class GameObjectController {
    /**
     * ロガー.
     */
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * 格納しているモデル.
     */
    public GameObjectModel model;

    /**
     * ゲームオブジェクトコントローラを生成する.
     * 
     * @param model 対象のモデル
     */
    public GameObjectController(GameObjectModel model) {
        this.model = model;
    }

    /**
     * 毎フレーム呼ばれる.
     * 
     * @param dt 前フレームからの経過時間
     */
    public abstract void update(double dt);

    /**
     * update()の前に呼ばれる.
     *
     * @param dt 前フレームからの経過時間
     */
    public void preUpdate(double dt) {
    }

    /**
     * update()の後に呼ばれる.
     * 
     * @param dt 前フレームからの経過時間
     */
    public void postUpdate(double dt) {
    }

    /**
     * setupWorld()が実行された後に呼ばれる.
     */
    public void postSetupWorld() {
    }

    public void dispose() {
    }
}
