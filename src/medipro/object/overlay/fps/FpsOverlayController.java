package medipro.object.overlay.fps;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * FPSをオーバーレイ表示するコントローラ.
 */
public class FpsOverlayController extends GameObjectController {

    /**
     * FPSをオーバーレイ表示するコントローラを生成する.
     * 
     * @param model 対象のモデル
     */
    public FpsOverlayController(GameObjectModel model) {
        super(model);
    }

    @Override
    public void update(double dt) {
        FpsOverlayModel fpsOverlayModel = (FpsOverlayModel) model;
        fpsOverlayModel.updateFpsHistory((short) (1 / dt));
    }
}
