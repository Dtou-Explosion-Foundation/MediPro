package medipro.world;

import java.util.Optional;

import javax.swing.JPanel;

import medipro.object.base.camera.CameraController;
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
            SmoothFollowingCameraModel model = new SmoothFollowingCameraModel(cameraTarget);
            model.scale = 2;
            model.originY = (int) (10 / model.scale);
            CameraView view = new CameraView(model);
            CameraController controller = new CameraController(model, view);
            this.addController(controller);
            camera = Optional.of(controller);
        }
    }
}
