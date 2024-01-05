package medipro.object.ornament.glmarker;

import java.awt.Graphics2D;

import medipro.object.base.gameobject.GameObjectView;

public class GLMarkerView extends GameObjectView {

    public GLMarkerView(GLMarkerModel model) {
        super(model);
    }

    @Override
    public void draw(Graphics2D g) {
        GLMarkerModel glMarkerModel = (GLMarkerModel) model;
        g.setColor(glMarkerModel.getColor());
        g.fillOval(-150, -200, 300, 400);
    }

}
