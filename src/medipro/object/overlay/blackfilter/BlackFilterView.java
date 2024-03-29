package medipro.object.overlay.blackfilter;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import medipro.config.InGameConfig;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

/**
 * 暗転のビュー.
 */
public class BlackFilterView extends GameObjectView {

    /**
     * 暗転のビューを生成する.
     * 
     * @param model 対象のモデル
     */
    public BlackFilterView(GameObjectModel model) {
        super(model);
    }

    @Override
    protected void draw(Graphics2D g) {
        int windowWidth = (int) (InGameConfig.WINDOW_WIDTH
                * model.getWorld().getPanel().getFrame().getScreenScaleFactor());
        int windowHeight = (int) (InGameConfig.WINDOW_HEIGHT
                * model.getWorld().getPanel().getFrame().getScreenScaleFactor());
        g.setTransform(new AffineTransform());
        g.setColor(((BlackFilterModel) model).getColor());
        g.fillRect(0, 0, windowWidth, windowHeight);
    }

}
