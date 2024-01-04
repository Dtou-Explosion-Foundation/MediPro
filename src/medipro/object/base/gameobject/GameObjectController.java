
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
     * @param models 格納するモデル
     */
    public GameObjectController(GameObjectModel model) {
        this.model = model;
    }

    /**
     * 格納しているモデルに対してそれぞれupdate()を呼び出す.
     * 
     * @param dt 前フレームからの経過時間
     */
    public abstract void update(double dt);
}
