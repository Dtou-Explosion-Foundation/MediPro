package medipro.object.base.gridobject;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * グリッドオブジェクトのコントローラ.
 */
public abstract class GridObjectController extends GameObjectController {

    /**
     * グリッドオブジェクトのコントローラを生成する.
     * 
     * @param model 対象のモデル
     */
    public GridObjectController(GameObjectModel model) {
        super(model);
    }

    @Override
    public void update(double dt) {
    }
}
