package medipro.object.ornament.marker;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

public class MarkerController extends GameObjectController {

    public MarkerController(GameObjectModel... models) {
        super(models);
    }

    @Override
    public void update(GameObjectModel model, float dt) {
        MarkerModel markerModel = (MarkerModel) model;
        markerModel.rotation += 1f * dt;
        markerModel.scaleX += 1 * dt;
        if (markerModel.scaleX > 5) {
            markerModel.scaleX = 0;
        }
    }

}
