package medipro.world;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import medipro.config.EngineConfig;
import medipro.object.base.World;
import medipro.object.base.camera.CameraController;
import medipro.object.base.camera.CameraView;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.camera.SmoothFollowingCameraController;
import medipro.object.camera.SmoothFollowingCameraModel;
import medipro.object.example.grid.ExampleGridController;
import medipro.object.example.grid.ExampleGridModel;
import medipro.object.example.grid.ExampleGridView;
import medipro.object.manager.gamemanager.GameManagerController;
import medipro.object.manager.gamemanager.GameManagerModel;
import medipro.object.ornament.marker.MarkerController;
import medipro.object.ornament.marker.MarkerModel;
import medipro.object.ornament.marker.MarkerView;
import medipro.object.ornament.texture.TextureController;
import medipro.object.ornament.texture.TextureModel;
import medipro.object.ornament.texture.TextureView;
import medipro.object.overlay.blackfilter.BlackFilterController;
import medipro.object.overlay.blackfilter.BlackFilterModel;
import medipro.object.overlay.blackfilter.BlackFilterView;
import medipro.object.overlay.fps.FpsOverlayController;
import medipro.object.overlay.fps.FpsOverlayModel;
import medipro.object.overlay.fps.FpsOverlayView;
import medipro.object.player.PlayerController;
import medipro.object.player.PlayerModel;
import medipro.object.player.PlayerView;
import medipro.object.stairs.StairsController;
import medipro.object.stairs.StairsModel;
import medipro.object.stairs.StairsView;

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
        GameObjectModel cameraTarget = null;

        // {
        //     BackgroundModel model = new BackgroundModel(this);
        //     BackgroundView view = new BackgroundView(model);
        //     // BackgroundController controller = new BackgroundController(model);
        //     this.addView(view, 0);
        // }
        {
            PlayerModel model = new PlayerModel(this);
            cameraTarget = model;
            PlayerView view = new PlayerView(model);
            PlayerController controller = new PlayerController(model);
            if (panel == null)
                logger.warning("Panel is null");
            panel.addKeyListener(controller);
            this.addViewAndController(view, controller, 10);
        }
        {
            // MarkerController controller = new MarkerController();
            {
                MarkerModel model = new MarkerModel(this);
                MarkerView view = new MarkerView(model);
                model.x = 100;
                model.y = -25;
                model.rotation = Math.toRadians(30);
                model.scaleX = 3.5;
                model.scaleY = 1.5;
                model.color = Color.BLUE;
                MarkerController controller = new MarkerController(model);
                this.addViewAndController(view, controller, 20);
            }
            {
                MarkerModel model = new MarkerModel(this);
                MarkerView view = new MarkerView(model);
                model.x = -100;
                model.y = -50;
                model.rotation = Math.toRadians(70);
                model.color = Color.GREEN;
                MarkerController controller = new MarkerController(model);
                this.addViewAndController(view, controller, 20);
            }
            {
                MarkerModel model = new MarkerModel(this);
                MarkerView view = new MarkerView(model);
                MarkerController controller = new MarkerController(model);
                this.addViewAndController(view, controller, 20);
            }
        }
        {
            ExampleGridModel model = null;
            try {
                model = new ExampleGridModel(this, ImageIO.read(new File("img/background/Brickwall3_Texture.png")));
            } catch (IOException e) {
                logger.warning(e.toString());
            }
            ExampleGridView view = new ExampleGridView(model);
            ExampleGridController controller = new ExampleGridController(model);
            // model.x = 58;
            // model.y = -100;
            model.scaleX = 0.1;
            model.scaleY = 0.1;
            // if (model != null)
            //     view.models.add(model);
            this.addViewAndController(view, controller, 0);

        }
        {
            TextureModel model = new TextureModel(this,
                    new String[] { "img/pigeon/pigeon_white_sitting.png", "img/pigeon/pigeon_white_standing.png" });
            model.scaleX = 0.075;
            model.scaleY = 0.075;
            model.y = 10;
            TextureView view = new TextureView(model);
            TextureController controller = new TextureController(model);
            this.addViewAndController(view, controller, 0);
        }
        {
            TextureModel model = new TextureModel(this,
                    new String[] { "img/pigeon/pigeon_normal_sitting.png", "img/pigeon/pigeon_normal_standing.png" });
            model.scaleX = 0.075;
            model.scaleY = 0.075;
            model.x = -100;
            model.y = 20;
            TextureView view = new TextureView(model);
            TextureController controller = new TextureController(model);
            this.addViewAndController(view, controller, 0);
        }
        {
            StairsModel model = new StairsModel(this);
            model.x = -500;
            model.y = 10;
            model.setTriggerRange(300f);
            model.setLeftUp(true);
            StairsView view = new StairsView(model);
            StairsController controller = new StairsController(model);
            this.addViewAndController(view, controller, 5);
        }
        {
            StairsModel model = new StairsModel(this);
            model.x = 500;
            model.y = 10;
            model.setTriggerRange(300f);
            StairsView view = new StairsView(model);
            StairsController controller = new StairsController(model);
            this.addViewAndController(view, controller, 5);
        }
        {
            FpsOverlayModel model = new FpsOverlayModel(this);
            FpsOverlayView view = new FpsOverlayView(model);
            FpsOverlayController controller = new FpsOverlayController(model);
            this.addViewAndController(view, controller, 100);
        }
        {
            SmoothFollowingCameraModel model = new SmoothFollowingCameraModel(this, cameraTarget);
            model.setScale(2);
            model.followingSpeed = 0.08;
            // model.originY = (int) (-100 / model.getScale());
            // model.originY = 50;
            model.setMinX(-300);
            model.setMaxX(300);
            model.setLockY(true);
            model.y = 50;
            CameraView view = new CameraView(model);
            CameraController controller = new SmoothFollowingCameraController(model);
            // CameraController controller = new FollowingCameraController(model);
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
