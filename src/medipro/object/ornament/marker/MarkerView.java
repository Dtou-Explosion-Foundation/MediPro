package medipro.object.ornament.marker;

import java.awt.Graphics2D;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

/**
 * マーカーのビュー.
 */
public class MarkerView extends GameObjectView {

    /**
     * モデルを元に描画を行う. テスト用の図形を描画する.
     * 
     * @param model 描画対象のモデル
     * @param g     描画対象のGraphics2D
     */
    @Override
    public void draw(GameObjectModel model, Graphics2D g) {
        MarkerModel markerModel = (MarkerModel) model;
        g.transform(model.getTransformMatrix());
        g.setColor(markerModel.color);
        // g.fillOval(-markerModel.radius / 2, -markerModel.radius / 2,
        // markerModel.radius,
        g.fillRect(-markerModel.radius / 2, -markerModel.radius / 2, markerModel.radius, markerModel.radius);
    }
}
