package medipro.object.base.gridobject;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * グリッドオブジェクトのコントローラ.
 */
public abstract class GridObjectController extends GameObjectController {

    public GridObjectController(GameObjectModel model) {
        super(model);
    }

    /**
     * グリッドオブジェクトのコントローラを生成する.
     */
    @Override
    public void update(double dt) {
    }
}
