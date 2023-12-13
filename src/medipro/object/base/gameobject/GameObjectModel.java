package medipro.object.base.gameobject;

import java.awt.geom.AffineTransform;
import java.util.logging.Logger;

public abstract class GameObjectModel {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    public int x;
    public int y;

    // TODO: add rotation, scale, etc.

    abstract public void update(float dt);

    public AffineTransform getTransformMatrix() {
        AffineTransform transform = new AffineTransform();
        transform.translate(x, y);
        return transform;
    }
}
