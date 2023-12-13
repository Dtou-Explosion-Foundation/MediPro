package medipro.world;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

import javax.swing.JPanel;

import medipro.config.Config;
import medipro.object.base.camera.CameraController;
import medipro.object.base.camera.CameraModel;
import medipro.object.base.gameobject.GameObjectController;

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

    public void updateAndDraw(Graphics2D g, float dt) {
        AffineTransform transform;
        if (camera.isPresent()) {
            CameraController _camera = camera.get();
            transform = ((CameraModel) _camera.model).getTransformMatrix();
        } else {
            transform = new AffineTransform();
            transform.translate(Config.WINDOW_WIDTH / 2, Config.WINDOW_HEIGHT / 2);
        }
        g.transform(transform);
        for (GameObjectController controller : controllers) {
            controller.update(dt);
        }
        for (GameObjectController controller : controllers) {
            controller.draw(g);
        }
    }

}
