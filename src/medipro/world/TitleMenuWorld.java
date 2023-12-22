package medipro.world;

import javax.swing.JPanel;

import medipro.titlemenu.TitleMenuModel;
import medipro.titlemenu.TitleMenuView;
import medipro.titlemenu.TitleMenuController;

public class TitleMenuWorld extends World {
    public TitleMenuWorld(JPanel panel) {
        super(panel);
    }

    @Override
    public void setupWorld(JPanel panel) {
        {
            TitleMenuModel model = new TitleMenuModel(this);
            TitleMenuView view = new TitleMenuView(model);
            TitleMenuController controller = new TitleMenuController(model);
            this.addViewAndController(view, controller);
            panel.addKeyListener(controller);
        }
    }
}
