package medipro.world;

import javax.swing.JPanel;

import medipro.object.base.World;
import medipro.titlemenu.TitleMenuController;
import medipro.titlemenu.TitleMenuModel;
import medipro.titlemenu.TitleMenuView;

public class TitleMenuWorld extends World {
    public TitleMenuWorld(JPanel panel) {
        super(panel);
    }

    @Override
    public void setupWorld(JPanel panel) {
        {
            TitleMenuModel model = new TitleMenuModel(this);
            model.x = -400;
            TitleMenuView view = new TitleMenuView(model);
            TitleMenuController controller = new TitleMenuController(model);
            this.addViewAndController(view, controller);
            panel.addKeyListener(controller);
        }
    }
}
