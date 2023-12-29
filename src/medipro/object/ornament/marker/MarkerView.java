package medipro.object.ornament.marker;

import java.awt.Graphics2D;

import medipro.object.base.gameobject.GameObjectView;

/**
 * マーカーのビュー.
 */
public class MarkerView extends GameObjectView {

    public MarkerView(MarkerModel model) {
        super(model);
    }

    /**
     * モデルを元に描画を行う. テスト用の図形を描画する.
     * 
     * @param g 描画対象のGraphics2D
     */
    @Override
    public void draw(Graphics2D g) {
        MarkerModel markerModel = (MarkerModel) model;
        g.setColor(markerModel.color);
        // g.fillOval(-markerModel.radius / 2, -markerModel.radius / 2,
        // markerModel.radius,
        g.fillRect(-markerModel.radius / 2, -markerModel.radius / 2, markerModel.radius, markerModel.radius);
    }
}
