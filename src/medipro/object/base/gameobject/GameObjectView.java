package medipro.object.base.gameobject;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.logging.Logger;

public abstract class GameObjectView {
    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public ArrayList<GameObjectModel> models;

    public GameObjectView() {
        this.models = new ArrayList<GameObjectModel>();
    }

    public GameObjectView(GameObjectModel... models) {
        this();
        for (GameObjectModel model : models) {
            this.models.add(model);
        }
    }

    public void drawModels(Graphics2D g) {
        for (GameObjectModel model : models) {
            this.draw(model, g);
        }
    }

    abstract public void draw(GameObjectModel model, Graphics2D g);
}
