package worlds;

import java.util.Optional;

import javax.swing.JPanel;

import objects.FollowingCamera.SmoothFollowingCameraModel;
import objects.Player.PlayerController;
import objects.Player.PlayerModel;
import objects.Player.PlayerView;
import objects.base.Camera.CameraController;
import objects.base.Camera.CameraModel;
import objects.base.Camera.CameraView;
import objects.base.GameObject.GameObjectModel;
import objects.ornaments.Marker.MarkerContoller;
import objects.ornaments.Marker.MarkerModel;
import objects.ornaments.Marker.MarkerView;

public class TestWorld extends World {

    public TestWorld(JPanel panel) {
        super(panel);
    }

    @Override
    public void setupWorld(JPanel panel) {
        GameObjectModel cameraTraget;
        {
            PlayerModel model = new PlayerModel();
            cameraTraget = model;
            PlayerView view = new PlayerView(model);
            PlayerController controller = new PlayerController(model, view);
            panel.addKeyListener(controller);
            this.addController(controller);
        }
        {
            MarkerModel model = new MarkerModel();
            MarkerView view = new MarkerView(model);
            MarkerContoller controller = new MarkerContoller(model, view);
            this.addController(controller);
        }
        {
            CameraModel model = new SmoothFollowingCameraModel(cameraTraget);
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
