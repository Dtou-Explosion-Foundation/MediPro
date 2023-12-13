package medipro.object.ornament.marker;

import java.awt.Color;
import java.awt.Graphics;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

public class MarkerView extends GameObjectView {

    public MarkerView(GameObjectModel model) {
        super(model);
    }

    @Override
    public void draw(Graphics g) {
        MarkerModel markerModel = (MarkerModel) model;
        g.setColor(Color.RED);
        g.fillOval(model.x - markerModel.radius / 2, model.y - markerModel.radius / 2, markerModel.radius,
                markerModel.radius);
    }
}
