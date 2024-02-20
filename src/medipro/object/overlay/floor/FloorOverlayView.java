package medipro.object.overlay.floor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import medipro.config.InGameConfig;
import medipro.object.base.gameobject.GameObjectView;
import medipro.object.manager.gamemanager.GameManagerModel;

/**
 * フロア表示のビュー.
 */
public class FloorOverlayView extends GameObjectView {
    /**
     * フロア表示のビューを生成する.
     * 
     * @param model 対象のモデル
     */
    public FloorOverlayView(FloorOverlayModel model) {
        super(model);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setTransform(new AffineTransform());
        int floor = GameManagerModel.getFloor();
        int windowWidth = (int) (InGameConfig.WINDOW_WIDTH
                * model.getWorld().getPanel().getFrame().getScreenScaleFactor());
        g.setFont(new Font("SansSerif", Font.BOLD, 75));
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(floor) + "層", windowWidth - 200, 175);
        g.setFont(new Font("SansSerif", Font.BOLD, 50));
        g.drawString("現在", windowWidth - 300, 100);
    }
}
