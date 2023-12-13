package medipro.object.base.gameobject;

import java.awt.geom.AffineTransform;
import java.util.logging.Logger;

import medipro.world.World;

public abstract class GameObjectModel {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    public World world;

    public int x;
    public int y;

    public double rotation;

    public double scaleX;
    public double scaleY;

    public GameObjectModel(World world) {
        this.world = world;

        this.x = 0;
        this.y = 0;

        this.rotation = 0;

        this.scaleX = 1;
        this.scaleY = 1;
    }

    public AffineTransform getTransformMatrix() {
        AffineTransform transform = new AffineTransform();
        transform.translate(x, y);
        transform.rotate(rotation);
        transform.scale(scaleX, scaleY);
        return transform;
    }
}
