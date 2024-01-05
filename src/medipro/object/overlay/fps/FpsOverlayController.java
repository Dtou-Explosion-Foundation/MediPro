package medipro.object.overlay.fps;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

public class FpsOverlayController extends GameObjectController {

    public FpsOverlayController(GameObjectModel model) {
        super(model);
    }

    @Override
    public void update(double dt) {
        FpsOverlayModel fpsOverlayModel = (FpsOverlayModel) model;
        fpsOverlayModel.updateFpsHistory((short) (1 / dt));
    }

}
