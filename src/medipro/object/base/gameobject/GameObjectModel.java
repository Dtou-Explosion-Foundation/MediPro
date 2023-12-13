package medipro.object.base.gameobject;

import java.awt.geom.AffineTransform;
import java.util.logging.Logger;

import medipro.world.World;

public abstract class GameObjectModel {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    public World world;
    public int x;
    public int y;

    public GameObjectModel(World world) {
        this.world = world;
        this.x = 0;
        this.y = 0;
    }

    // TODO: add rotation, scale, etc.

    // abstract public void update(float dt);

    public AffineTransform getTransformMatrix() {
        AffineTransform transform = new AffineTransform();
        transform.translate(x, y);
        return transform;
    }
}
