package objects.base.GameObject;

import java.util.logging.Logger;

public abstract class GameObjectModel {
    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public int x;
    public int y;

    abstract public void update(float dt);
}
