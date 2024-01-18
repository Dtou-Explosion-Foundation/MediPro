package medipro.world;

import java.io.IOException;
import java.util.Optional;

import javax.swing.JPanel;

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
import medipro.object.overlay.vignette.VignetteController;
import medipro.object.overlay.vignette.VignetteModel;
import medipro.object.overlay.vignette.VignetteView;
import medipro.object.player.PlayerController;
import medipro.object.player.PlayerModel;
import medipro.object.player.PlayerView;
import medipro.object.stage.background.BackgroundController;
import medipro.object.stage.background.BackgroundModel;
import medipro.object.stage.background.BackgroundView;
import medipro.object.stage.ceil.CeilController;
import medipro.object.stage.ceil.CeilModel;
import medipro.object.stage.ceil.CeilView;
import medipro.object.stage.floor.FloorController;
import medipro.object.stage.floor.FloorModel;
import medipro.object.stage.floor.FloorView;
import medipro.object.stage.wall.WallController;
import medipro.object.stage.wall.WallModel;
import medipro.object.stage.wall.WallView;
import medipro.object.stairs.StairsController;
import medipro.object.stairs.StairsModel;
import medipro.object.stairs.StairsView;

public class PlayWorld extends World {

    public PlayWorld(JPanel panel) {
        super(panel);
        logger.info("PlayWorld created");
    }

    @Override
    public void setupWorld(JPanel panel) {
        // Layers
        // 00X: Background
        // 01X: Ornament
        // 02X: Stairs
        // 03X: Ornament
        // 04X: Ornament
        // 05X: Player
        // 06X: Ornament
        // 07X: Ornament
        // 08X: Wall
        // 10X: Overlay
        // 11X: GUI
        GameObjectModel cameraTarget = null;
        {
            PlayerModel model = new PlayerModel(this);
            cameraTarget = model;
            model.y = -70;
            model.x = -400;
            PlayerView view = new PlayerView(model);
            PlayerController controller = new PlayerController(model);
            panel.addKeyListener(controller);
            this.addViewAndController(view, controller, 50);
        }
        {
            FpsOverlayModel model = new FpsOverlayModel(this);
            FpsOverlayView view = new FpsOverlayView(model);
            FpsOverlayController controller = new FpsOverlayController(model);
            this.addViewAndController(view, controller, 110);
        }
        {
            VignetteModel model = new VignetteModel(this);
            VignetteView view = new VignetteView(model);
            VignetteController controller = new VignetteController(model);
            this.addViewAndController(view, controller, 100);
        }
        {
            StairsModel model = new StairsModel(this);
            model.x = -500;
            model.y = -65;
            model.setTriggerRange(100f);
            model.setLeftUp(true);
            StairsView view = new StairsView(model);
            StairsController controller = new StairsController(model);
            this.addViewAndController(view, controller, 20);
        }
        {
            StairsModel model = new StairsModel(this);
            model.x = 500;
            model.y = -65;
            model.setTriggerRange(100f);
            StairsView view = new StairsView(model);
            StairsController controller = new StairsController(model);
            this.addViewAndController(view, controller, 20);
        }
        {
            WallModel model = new WallModel(this);
            model.x = 2 - 00;
            WallView view = new WallView(model);
            WallController controller = new WallController(model);
            this.addViewAndController(view, controller, 80);
        }
        {
            try {
                BackgroundModel model = new BackgroundModel(this);
                BackgroundController controller = new BackgroundController(model);
                BackgroundView view = new BackgroundView(model);
                this.addViewAndController(view, controller, 0);
            } catch (IOException e) {
                logger.severe("Failed to load background image");
                e.printStackTrace();
            }
        }
        {
            try {
                CeilModel model = new CeilModel(this);
                model.y = -200;
                CeilController controller = new CeilController(model);
                CeilView view = new CeilView(model);
                this.addViewAndController(view, controller, 1);
            } catch (IOException e) {
                logger.severe("Failed to load ceil image");
                e.printStackTrace();
            }
        }
        {
            try {
                FloorModel model = new FloorModel(this);
                model.y = 100;
                FloorController controller = new FloorController(model);
                FloorView view = new FloorView(model);
                this.addViewAndController(view, controller, 1);
            } catch (IOException e) {
                logger.severe("Failed to load floor image");
                e.printStackTrace();
            }

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
            this.addViewAndController(view, controller, 100);
        }
        {
            GameManagerModel model = new GameManagerModel(this);
            GameManagerController controller = new GameManagerController(model);
            this.addControllers(controller);
        }
    }
}