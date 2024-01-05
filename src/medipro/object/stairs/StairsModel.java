package medipro.object.stairs;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

public class StairsModel extends GameObjectModel {

    private boolean isLeftUp = false;
    private float width;

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    private float height;

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isLeftUp() {
        return isLeftUp;
    }

    public void setLeftUp(boolean isLeftUp) {
        this.isLeftUp = isLeftUp;
    }

    public StairsModel(World world) {
        super(world);
    }

}
