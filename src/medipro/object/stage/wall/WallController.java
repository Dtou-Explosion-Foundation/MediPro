package medipro.object.stage.wall;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * 壁のコントローラ.
 */
public class WallController extends GameObjectController {

    /**
     * 壁のコントローラを生成する.
     * 
     * @param model 対象のモデル
     */
    public WallController(GameObjectModel model) {
        super(model);
    }

    @Override
    public void update(double dt) {
        if (this.model.getWorld().getCamera().isPresent()) {
            this.model.setY(this.model.getWorld().getCamera().get().getY());
        }
    }
}
