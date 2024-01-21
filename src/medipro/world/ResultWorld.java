package medipro.world;

import javax.swing.JPanel;

import medipro.object.base.World;
import medipro.object.overlay.blackfilter.BlackFilterController;
import medipro.object.overlay.blackfilter.BlackFilterModel;
import medipro.object.overlay.blackfilter.BlackFilterModel.BlackFilterColor;
import medipro.object.overlay.blackfilter.BlackFilterView;
import medipro.result.ResultController;
import medipro.result.ResultModel;
import medipro.result.ResultView;

public class ResultWorld extends World {
    public ResultWorld(JPanel panel) {
        super(panel);
    }

    @Override
    public void setupWorld(JPanel panel) {
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
            model.x = -400;
            ResultView view = new ResultView(model);
            ResultController controller = new ResultController(model);
            this.addViewAndController(view, controller);
            panel.addKeyListener(controller);
        }
    }
}