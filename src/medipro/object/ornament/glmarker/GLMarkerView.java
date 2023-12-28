package medipro.object.ornament.glmarker;

import java.awt.Color;
import java.awt.Graphics2D;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

public class GLMarkerView extends GameObjectView {

    public GLMarkerView(GameObjectModel model) {
        super(model);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillOval(-150, -200, 300, 400);
    }

}
