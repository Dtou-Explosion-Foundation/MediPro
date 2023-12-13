package medipro.object.ornament.marker;

import java.awt.Graphics2D;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

/**
 * マーカーのビュー.
 */
public class MarkerView extends GameObjectView {

    /**
     * {@inheritDoc}
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
