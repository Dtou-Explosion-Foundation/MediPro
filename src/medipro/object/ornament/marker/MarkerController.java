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
     * @param model 対象のモデル
     */
    public MarkerController(GameObjectModel model) {
        super(model);
    }

    /**
     * モデルを次フレームの状態に更新する. 一秒に1radiusだけ回転させる. また一秒に1scaleXだけ拡大させ、5を超えたら0に戻す.
     * 
     * @param dt 前フレームからの経過時間
     */
    @Override
    public void update(double dt) {
        MarkerModel markerModel = (MarkerModel) model;
        markerModel.rotation += 1f * dt;
        markerModel.scaleX += 1 * dt;
        if (markerModel.scaleX > 5) {
            markerModel.scaleX = 0;
        }
    }

}
