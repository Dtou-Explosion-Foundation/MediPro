package medipro.object.base.gameobject;

import java.awt.Graphics;
import java.util.logging.Logger;

public abstract class GameObjectView {
    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public GameObjectModel model;

    public GameObjectView(GameObjectModel model) {
        this.model = model;
    }

    abstract public void draw(Graphics g);
}
