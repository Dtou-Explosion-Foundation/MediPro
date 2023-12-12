import java.awt.Graphics;
import java.time.Duration;
import java.util.ArrayList;
import java.util.logging.Logger;

import objects.base.GameObject.GameObjectController;

public class World {
    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    ArrayList<GameObjectController> controllers;

    public World() {
        logger.info("Init World");
    }

    public void addController(GameObjectController controller) {
        controllers.add(controller);
    }

    public void updateAndDraw(Graphics g, Duration deltaTime) {
        controllers = new ArrayList<GameObjectController>();
        for (GameObjectController controller : controllers) {
            controller.update(deltaTime.toNanos() / 1000000000f);
        }
        for (GameObjectController controller : controllers) {
            controller.draw(g);
        }
    }

}
