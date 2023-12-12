package worlds;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

import javax.swing.JPanel;

import configs.Config;
import objects.base.Camera.CameraController;
import objects.base.GameObject.GameObjectController;

public abstract class World {
    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    ArrayList<GameObjectController> controllers;

    JPanel panel;
    Optional<CameraController> camera = Optional.empty();

    public World(JPanel panel) {
        logger.info("Init World");
        this.controllers = new ArrayList<GameObjectController>();
        setupWorld(panel);
    }

    public void addController(GameObjectController controller) {
        this.controllers.add(controller);
    }

    public abstract void setupWorld(JPanel panel);

    public void updateAndDraw(Graphics g, float dt) {
        int cameraX = 0;
        int cameraY = 0;
        if (camera.isPresent()) {
            CameraController _camera = camera.get();
            cameraX -= _camera.model.x;
            cameraY += _camera.model.y;
        }
        cameraX += Config.WINDOW_WIDTH / 2;
        cameraY += Config.WINDOW_HEIGHT / 2;

        g.translate(cameraX, cameraY);

        for (GameObjectController controller : controllers) {
            controller.update(dt);
        }
        for (GameObjectController controller : controllers) {
            controller.draw(g);
        }
    }

}
