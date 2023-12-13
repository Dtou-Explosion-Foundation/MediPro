package medipro.object.base.gameobject;

import java.awt.Graphics;
import java.util.logging.Logger;

public abstract class GameObjectController {
    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public GameObjectModel model;
    public GameObjectView view;

    public GameObjectController(GameObjectModel model, GameObjectView view) {
        this.model = model;
        this.view = view;
    }

    public void update(float dt) {
        model.update(dt);
    }

    public void draw(Graphics g) {
        view.draw(g);
    }
}
