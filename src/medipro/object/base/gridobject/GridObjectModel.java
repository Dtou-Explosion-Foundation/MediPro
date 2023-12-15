package medipro.object.base.gridobject;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

public abstract class GridObjectModel extends GameObjectModel {

    public int width;
    public int height;

    public GridObjectModel(World world, int width, int height) {
        super(world);
        this.width = width;
        this.height = height;
    }
}
