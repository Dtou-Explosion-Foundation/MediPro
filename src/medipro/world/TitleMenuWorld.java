package medipro.world;

import java.util.Optional;

import javax.swing.JPanel;

import medipro.gui.panel.G2dGamePanel;
import medipro.object.base.World;
import medipro.object.base.camera.CameraController;
import medipro.object.base.camera.CameraModel;
import medipro.object.base.camera.CameraView;
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
    public TitleMenuWorld(G2dGamePanel panel) {
        super(panel);
    }

    @Override
    public void setupWorld(JPanel panel) {
        {
            CameraModel model = new CameraModel(this);
            CameraView view = new CameraView(model);
            CameraController controller = new CameraController(model);
            this.addViewAndController(view, controller);
            this.camera = Optional.of(model);
        }
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