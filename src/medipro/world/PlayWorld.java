package medipro.world;

import java.util.Optional;

import javax.swing.JPanel;

import medipro.config.EngineConfig;
import medipro.object.base.World;
import medipro.object.base.camera.CameraController;
import medipro.object.base.camera.CameraView;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.camera.SmoothFollowingCameraController;
import medipro.object.camera.SmoothFollowingCameraModel;
import medipro.object.manager.gamemanager.GameManagerController;
import medipro.object.manager.gamemanager.GameManagerModel;
import medipro.object.overlay.blackfilter.BlackFilterController;
import medipro.object.overlay.blackfilter.BlackFilterModel;
import medipro.object.overlay.blackfilter.BlackFilterView;
import medipro.object.overlay.fps.FpsOverlayController;
import medipro.object.overlay.fps.FpsOverlayModel;
import medipro.object.overlay.fps.FpsOverlayView;
import medipro.object.player.PlayerController;
import medipro.object.player.PlayerModel;
import medipro.object.player.PlayerView;
import medipro.object.stage.floor.FloorController;
import medipro.object.stage.floor.FloorModel;
import medipro.object.stage.floor.FloorView;

public class PlayWorld extends World {

    public PlayWorld(JPanel panel) {
        super(panel);
        logger.info("PlayWorld created");
    }

    @Override
    public void setupWorld(JPanel panel) {
        GameObjectModel cameraTarget = null;
        {
            PlayerModel model = new PlayerModel(this);
            cameraTarget = model;
            PlayerView view = new PlayerView(model);
            PlayerController controller = new PlayerController(model);
            panel.addKeyListener(controller);
            this.addViewAndController(view, controller, 10);
        }
        {
            FpsOverlayModel model = new FpsOverlayModel(this);
            FpsOverlayView view = new FpsOverlayView(model);
            FpsOverlayController controller = new FpsOverlayController(model);
            this.addViewAndController(view, controller, 100);
        }
        {
            FloorModel model = new FloorModel(this, 100, 100);
            model.y = 100;
            FloorController controller = new FloorController(model);
            FloorView view = new FloorView(model);
            this.addViewAndController(view, controller);
        }

        {
            SmoothFollowingCameraModel model = new SmoothFollowingCameraModel(this, cameraTarget);
            model.setScale(2);
            model.followingSpeed = 0.08;
            model.setMinX(-300);
            model.setMaxX(300);
            model.setLockY(true);
            model.y = 50;
            CameraView view = new CameraView(model);
            CameraController controller = new SmoothFollowingCameraController(model);
            this.addViewAndController(view, controller);
            camera = Optional.of(model);
        }
        {
            BlackFilterModel model = new BlackFilterModel(this);
            BlackFilterController controller = new BlackFilterController(model);
            BlackFilterView view = new BlackFilterView(model);
            model.setAlpha(1f);
            controller.blackOut(2f);
            this.addViewAndController(view, controller, EngineConfig.LAYER_NUM - 1);
        }
        {
            GameManagerModel model = new GameManagerModel(this);
            GameManagerController controller = new GameManagerController(model);
            this.addControllers(controller);
        }
    }
}
