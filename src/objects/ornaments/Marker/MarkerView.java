package objects.ornaments.Marker;

import java.awt.Color;
import java.awt.Graphics;

import objects.base.GameObject.GameObjectModel;
import objects.base.GameObject.GameObjectView;

public class MarkerView extends GameObjectView {

    public MarkerView(GameObjectModel model) {
        super(model);
    }

    @Override
    public void draw(Graphics g) {
        MarkerModel markerModel = (MarkerModel) model;
        g.setColor(Color.RED);
        g.fillOval(model.x - markerModel.raduis / 2, model.y - markerModel.raduis / 2, markerModel.raduis,
                markerModel.raduis);
    }
}
