package medipro.object.base.gridobject;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * グリッドオブジェクトのコントローラ.
 */
public abstract class GridObjectController extends GameObjectController {

    public GridObjectController(GameObjectModel... models) {
        super(models);
    }

    /**
     * グリッドオブジェクトのコントローラを生成する.
     */
    @Override
    public void update(GameObjectModel model, double dt) {
    }
}
