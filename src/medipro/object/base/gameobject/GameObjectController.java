package medipro.object.base.gameobject;

import java.util.ArrayList;
import java.util.logging.Logger;

public abstract class GameObjectController {
    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public ArrayList<GameObjectModel> models;
    // public GameObjectView view;

    public GameObjectController() {
        this.models = new ArrayList<GameObjectModel>();
    }

    public GameObjectController(GameObjectModel... models) {
        this();
        for (GameObjectModel model : models) {
            this.models.add(model);
        }
    }

    public void updateModels(float dt) {
        for (GameObjectModel model : models) {
            this.update(model, dt);
        }
    }

    abstract public void update(GameObjectModel model, float dt);
}
