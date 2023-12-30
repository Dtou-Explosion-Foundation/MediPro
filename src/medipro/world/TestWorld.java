package medipro.world;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import medipro.object.base.World;
import medipro.object.base.camera.CameraController;
import medipro.object.base.camera.CameraView;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.camera.SmoothFollowingCameraController;
import medipro.object.camera.SmoothFollowingCameraModel;
import medipro.object.example.grid.ExampleGridController;
import medipro.object.example.grid.ExampleGridModel;
import medipro.object.example.grid.ExampleGridView;
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
        logger.info("TestWorld created");
    }

    /**
     * {@inheritDoc}
     */
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
            MarkerController controller = new MarkerController();
            this.addControllers(controller);
            {
                MarkerModel model = new MarkerModel(this);
                MarkerView view = new MarkerView(model);
                model.x = 100;
                model.y = -25;
                model.rotation = Math.toRadians(30);
                model.scaleX = 3.5;
                model.scaleY = 1.5;
                model.color = Color.BLUE;
                controller.models.add(model);
                this.addView(view, 20);
            }
            {
                MarkerModel model = new MarkerModel(this);
                MarkerView view = new MarkerView(model);
                model.x = -100;
                model.y = -50;
                model.rotation = Math.toRadians(70);
                model.color = Color.GREEN;
                controller.models.add(model);
                this.addView(view, 20);
            }
            {
                MarkerModel model = new MarkerModel(this);
                MarkerView view = new MarkerView(model);
                controller.models.add(model);
                this.addView(view, 20);
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
            ExampleGridController controller = new ExampleGridController();
            model.x = 58;
            model.y = -100;
            model.scaleX = 0.1;
            model.scaleY = 0.1;
            // if (model != null)
            //     view.models.add(model);
            controller.models.add(model);
            this.addViewAndController(view, controller, 0);

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
