package medipro.world;

import java.util.Optional;

import javax.swing.JPanel;

import medipro.object.base.World;
import medipro.object.base.camera.CameraController;
import medipro.object.base.camera.CameraModel;
import medipro.object.base.camera.CameraView;
import medipro.object.ornament.glmarker.GLMarkerController;
import medipro.object.ornament.glmarker.GLMarkerModel;
import medipro.object.ornament.glmarker.GLMarkerView;

/**
 * テスト用のワールド
 */
public class GLWorld extends World {

    /**
     * テスト用のワールドを生成する.
     * 
     * @param panel ワールドが配置されているパネル
     */
    public GLWorld(JPanel panel) {
        super(panel);
        logger.info("GLWorld created");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupWorld(JPanel panel) {
        {
            GLMarkerModel model = new GLMarkerModel(this);
            GLMarkerView view = new GLMarkerView(model);
            GLMarkerController controller = new GLMarkerController(model);
            this.addViewAndController(view, controller, 10);
        }
        {
            CameraModel model = new CameraModel(this);
            model.scale = 1;
            CameraView view = new CameraView(model);
            CameraController controller = new CameraController(model);
            this.addViewAndController(view, controller);
            camera = Optional.of(model);
        }
    }
}
