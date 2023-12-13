package medipro.object.ornament.marker;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * マーカーのコントローラ.
 */
public class MarkerController extends GameObjectController {

    /**
     * マーカーコントローラを生成する.
     * 
     * @param models 格納するモデル
     */
    public MarkerController(GameObjectModel... models) {
        super(models);
    }

    /**
     * {@inheritDoc}
     */
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
