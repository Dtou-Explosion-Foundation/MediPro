package medipro.object.manager.gamemanager;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

public class GameManagerController extends GameObjectController {
    /**
     * ゲームオブジェクトコントローラを生成する.
     * 
     * @param models 格納するモデル
     */
    public GameManagerController(GameObjectModel... models) {
        super(models);
    }

    @Override
    public void update(GameObjectModel model, double dt) {

    }

}
