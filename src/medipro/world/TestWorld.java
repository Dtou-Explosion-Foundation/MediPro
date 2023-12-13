package medipro.world;

import java.awt.Color;
import java.util.Optional;

import javax.swing.JPanel;

import medipro.object.base.camera.CameraController;
import medipro.object.base.camera.CameraView;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.camera.SmoothFollowingCameraController;
import medipro.object.camera.SmoothFollowingCameraModel;
import medipro.object.ornament.marker.MarkerController;
import medipro.object.ornament.marker.MarkerModel;
import medipro.object.ornament.marker.MarkerView;
import medipro.object.player.PlayerController;
import medipro.object.player.PlayerModel;
import medipro.object.player.PlayerView;

/**
 * テスト用のワールド
 */
public class TestWorld extends World {

    /**
     * テスト用のワールドを生成する.
     * 
     * @param panel ワールドが配置されているパネル
     */
    public TestWorld(JPanel panel) {
        super(panel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupWorld(JPanel panel) {
        GameObjectModel cameraTarget;
        {
            PlayerModel model = new PlayerModel(this);
            cameraTarget = model;
            PlayerView view = new PlayerView(model);
            PlayerController controller = new PlayerController(model);
            panel.addKeyListener(controller);
            this.addViewAndController(view, controller, 10);
        }
        {
            MarkerView view = new MarkerView();
            MarkerController controller = new MarkerController();
            this.addViewAndController(view, controller, 20);
            {
                MarkerModel model = new MarkerModel(this);
                model.x = 100;
                model.y = -25;
                model.rotation = Math.toRadians(30);
                model.scaleX = 3.5;
                model.scaleY = 1.5;
                model.color = Color.BLUE;
                view.models.add(model);
                controller.models.add(model);
            }
            {
                MarkerModel model = new MarkerModel(this);
                model.x = -100;
                model.y = -50;
                model.rotation = Math.toRadians(70);
                model.color = Color.GREEN;
                view.models.add(model);
                controller.models.add(model);
            }
            {
                MarkerModel model = new MarkerModel(this);
                view.models.add(model);
                controller.models.add(model);
            }
        }
        {
            SmoothFollowingCameraModel model = new SmoothFollowingCameraModel(this, cameraTarget);
            model.scale = 2;
            model.originY = (int) (10 / model.scale);
            CameraView view = new CameraView(model);
            CameraController controller = new SmoothFollowingCameraController(model);
            this.addViewAndController(view, controller);
            camera = Optional.of(model);
        }
    }
}
