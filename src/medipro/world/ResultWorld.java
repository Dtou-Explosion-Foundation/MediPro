package medipro.world;

import java.util.Optional;

import javax.swing.JPanel;

import medipro.gui.panel.GamePanel;
import medipro.object.base.World;
import medipro.object.base.camera.CameraController;
import medipro.object.base.camera.CameraModel;
import medipro.object.base.camera.CameraView;
import medipro.object.overlay.blackfilter.BlackFilterController;
import medipro.object.overlay.blackfilter.BlackFilterModel;
import medipro.object.overlay.blackfilter.BlackFilterModel.BlackFilterColor;
import medipro.object.overlay.blackfilter.BlackFilterView;
import medipro.result.ResultController;
import medipro.result.ResultModel;
import medipro.result.ResultView;

/**
 * リザルト画面のワールド.
 */
public class ResultWorld extends World {
    /**
     * ResultWorldを生成する.
     * 
     * @param panel ワールドを表示するパネル
     */
    public ResultWorld(GamePanel panel) {
        super(panel);
    }

    @Override
    public void setupWorld(JPanel panel) {
        {
            CameraModel model = new CameraModel(this);
            CameraView view = new CameraView(model);
            CameraController controller = new CameraController(model);
            this.addViewAndController(view, controller);
            this.setCamera(Optional.of(model));
        }
        {
            BlackFilterModel model = new BlackFilterModel(this);
            BlackFilterController controller = new BlackFilterController(model);
            BlackFilterView view = new BlackFilterView(model);
            model.setAlpha(1f);
            model.setColor(BlackFilterColor.RED);
            controller.blackOut(2f);
            this.addViewAndController(view, controller, 100);
        }
        {
            BlackFilterModel model = new BlackFilterModel(this);
            BlackFilterController controller = new BlackFilterController(model);
            BlackFilterView view = new BlackFilterView(model);
            model.setAlpha(1f);
            model.setColor(BlackFilterColor.BLACK);
            controller.blackOut(0.5f);
            this.addViewAndController(view, controller, 101);
        }
        {
            ResultModel model = new ResultModel(this);
            model.setX(-400);
            ResultView view = new ResultView(model);
            ResultController controller = new ResultController(model);
            this.addViewAndController(view, controller);
            panel.addKeyListener(controller);
        }
    }
}
