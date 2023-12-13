package medipro.object.ornament.marker;

import java.awt.Graphics2D;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

public class MarkerView extends GameObjectView {

    @Override
    public void draw(GameObjectModel model, Graphics2D g) {
        MarkerModel markerModel = (MarkerModel) model;
        g.setColor(markerModel.color);
        g.fillOval(model.x - markerModel.radius / 2, model.y - markerModel.radius / 2, markerModel.radius,
                markerModel.radius);
    }
}
