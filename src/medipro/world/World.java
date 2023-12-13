package medipro.world;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

import javax.swing.JPanel;

import medipro.config.EngineConfig;
import medipro.config.InGameConfig;
import medipro.object.base.camera.CameraModel;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectView;

public abstract class World {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());
    public ArrayList<GameObjectController> controllers;
    public ArrayList<ArrayList<GameObjectView>> views;

    public JPanel panel;
    public Optional<CameraModel> camera = Optional.empty();

    public World(JPanel panel) {
        logger.info("Init World");
        this.controllers = new ArrayList<GameObjectController>();
        this.views = new ArrayList<ArrayList<GameObjectView>>();
        for (int i = 0; i < EngineConfig.LAYER_NUM; i++) {
            this.views.add(new ArrayList<GameObjectView>());
        }
        setupWorld(panel);
    }

    public void addControllers(GameObjectController... controllers) {
        for (GameObjectController controller : controllers)
            this.controllers.add(controller);
    }

    public void addViews(GameObjectView... views) {
        for (GameObjectView view : views)
            this.addView(view, EngineConfig.DEFAULT_LAYER);
    }

    public void addView(GameObjectView view, int layer) {
        this.views.get(layer).add(view);
    }

    public void addViewAndController(GameObjectView view, GameObjectController controller) {
        this.views.get(EngineConfig.DEFAULT_LAYER).add(view);
        this.controllers.add(controller);
    }

    public void addViewAndController(GameObjectView view, GameObjectController controller, int layer) {
        this.views.get(layer).add(view);
        this.controllers.add(controller);
    }

    public abstract void setupWorld(JPanel panel);

    public void updateAndDraw(Graphics2D g, float dt) {
        for (GameObjectController controller : controllers) {
            controller.updateModels(dt);
        }

        AffineTransform transform;
        if (camera.isPresent()) {
            CameraModel _camera = camera.get();
            transform = ((CameraModel) _camera).getTransformMatrix();
        } else {
            transform = new AffineTransform();
            transform.translate(InGameConfig.WINDOW_WIDTH / 2, InGameConfig.WINDOW_HEIGHT / 2);
        }

        for (ArrayList<GameObjectView> views : views) {
            for (GameObjectView view : views) {
                // g.setTransform(transform);
                view.drawModels(g, transform);
            }
        }
    }
}
