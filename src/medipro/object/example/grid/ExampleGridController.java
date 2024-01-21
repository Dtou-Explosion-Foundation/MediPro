package medipro.object.example.grid;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gridobject.GridObjectController;

/**
 * グリッドオブジェクトの例のコントローラ.
 */
public class ExampleGridController extends GridObjectController {

    /**
     * グリッドオブジェクトの例のコントローラを生成する.
     * 
     * @param model 対象のモデル
     */
    public ExampleGridController(GameObjectModel model) {
        super(model);
    }

    @Override
    public void update(double dt) {
    }

}
