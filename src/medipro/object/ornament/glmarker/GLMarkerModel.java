package medipro.object.ornament.glmarker;

import java.awt.Color;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

public class GLMarkerModel extends GameObjectModel {

    private Color color = Color.RED;

    public GLMarkerModel(World world) {
        super(world);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
