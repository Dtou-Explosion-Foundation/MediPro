package medipro.world;

import javax.swing.JPanel;

import medipro.object.base.World;
import medipro.result.ResultModel;
import medipro.result.ResultView;
import medipro.result.ResultController;

public class ResultWorld extends World {
    public ResultWorld(JPanel panel) {
        super(panel);
    }

    @Override
    public void setupWorld(JPanel panel) {
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