package medipro.object.stage.floor;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gridobject.GridObjectController;

/**
 * 床のコントローラ.
 */
public class FloorController extends GridObjectController {

    /**
     * 床のコントローラを生成する.
     * 
     * @param model 対象のモデル
     */
    public FloorController(GameObjectModel model) {
        super(model);
    }

}
