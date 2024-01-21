package medipro.object.base.camera;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * カメラのコントローラを実装するクラス.
 */
public class CameraController extends GameObjectController {

    /**
     * カメラコントローラを生成する.
     * 
     * @param model 対象のモデル
     */
    public CameraController(final GameObjectModel model) {
        super(model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(double dt) {

    }

}
