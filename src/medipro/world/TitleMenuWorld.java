package medipro.world;

import javax.swing.JPanel;

import medipro.object.base.World;
import medipro.titlemenu.TitleMenuController;
import medipro.titlemenu.TitleMenuModel;
import medipro.titlemenu.TitleMenuView;
import medipro.titlemenu.titlebackground.TitleBackgroundModel;
import medipro.titlemenu.titlebackground.TitleBackgroundView;

/**
 * タイトルメニューのワールド.
 */
public class TitleMenuWorld extends World {
    /**
     * タイトルメニューのワールドを生成する.
     * 
     * @param panel ワールドを表示するパネル
     */
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
            this.addViewAndController(view, controller, 100);
            panel.addKeyListener(controller);
        }
        {
            TitleBackgroundModel model = new TitleBackgroundModel(this);
            TitleBackgroundView view = new TitleBackgroundView(model);
            this.addView(view, 80);
        }
    }
}