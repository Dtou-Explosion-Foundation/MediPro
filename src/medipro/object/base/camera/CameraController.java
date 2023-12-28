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
     * @param models 格納するモデル
     */
    public CameraController(final GameObjectModel... models) {
        super(models);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final GameObjectModel model, final double dt) {

    }

}
