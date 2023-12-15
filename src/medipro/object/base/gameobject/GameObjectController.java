
package medipro.object.base.gameobject;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * ゲームオブジェクトのコントローラクラス. 複数のモデルを保持する. フレームの更新の通知を受け取り,モデルを更新する.
 * また、キー入力などのイベントを受け取り,モデルを更新する.
 */
public abstract class GameObjectController {
    /**
     * ロガー.
     */
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * 格納しているモデル.
     */
    public ArrayList<GameObjectModel> models;

    /**
     * ゲームオブジェクトコントローラを生成する.
     */
    public GameObjectController() {
        this.models = new ArrayList<GameObjectModel>();
    }

    /**
     * ゲームオブジェクトコントローラを生成する.
     * 
     * @param models 格納するモデル
     */
    public GameObjectController(GameObjectModel... models) {
        this();
        for (GameObjectModel model : models) {
            this.models.add(model);
        }
    }

    /**
     * 格納しているモデルに対してそれぞれupdate()を呼び出す.
     * 
     * @param dt 前フレームからの経過時間
     */
    public void updateModels(float dt) {
        for (GameObjectModel model : models) {
            this.update(model, dt);
        }
    }

    /**
     * モデルを次フレームの状態に更新する.
     * 
     * @param model 更新対象のモデル
     * @param dt    前フレームからの経過時間
     */
    public abstract void update(GameObjectModel model, float dt);
}
