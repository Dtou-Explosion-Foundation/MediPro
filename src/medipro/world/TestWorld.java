package medipro.world;

import java.util.Optional;

import javax.swing.JPanel;

import medipro.object.base.camera.CameraController;
import medipro.object.base.camera.CameraModel;
import medipro.object.base.camera.CameraView;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.camera.SmoothFollowingCameraModel;
import medipro.object.ornament.marker.MarkerController;
import medipro.object.ornament.marker.MarkerModel;
import medipro.object.ornament.marker.MarkerView;
import medipro.object.player.PlayerController;
import medipro.object.player.PlayerModel;
import medipro.object.player.PlayerView;

public class TestWorld extends World {

    public TestWorld(JPanel panel) {
        super(panel);
    }

    @Override
    public void setupWorld(JPanel panel) {
        GameObjectModel cameraTarget;
        {
            PlayerModel model = new PlayerModel();
            cameraTarget = model;
            PlayerView view = new PlayerView(model);
            PlayerController controller = new PlayerController(model, view);
            panel.addKeyListener(controller);
            this.addController(controller);
        }
        {
            MarkerModel model = new MarkerModel();
            MarkerView view = new MarkerView(model);
            MarkerController controller = new MarkerController(model, view);
            this.addController(controller);
        }
        {
            CameraModel model = new SmoothFollowingCameraModel(cameraTarget);
            CameraView view = new CameraView(model);
            CameraController controller = new CameraController(model, view);
            this.addController(controller);
            // controller.model.x = 300;
            // controller.model.y = 300;
            camera = Optional.of(controller);
            logger.info("Set Camera: " + controller.model.x + ", " + controller.model.y);

        }
    }
}
