package medipro.object.overlay.fps;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

/**
 * FPSを表示するオーバーレイのビュー.
 */
public class FpsOverlayView extends GameObjectView {

    /**
     * FpsOverlayViewを生成する.
     * 
     * @param model 対象のモデル
     */
    public FpsOverlayView(GameObjectModel model) {
        super(model);

    }

    /**
     * フォント.
     */
    private Font font = new Font("Arial", Font.BOLD, 12);

    @Override
    public void draw(Graphics2D g) {
        g.setTransform(new AffineTransform());

        FpsOverlayModel fpsOverlayModel = (FpsOverlayModel) model;
        g.setFont(font);
        g.setColor(fpsOverlayModel.getColor());
        FontMetrics fm = g.getFontMetrics();
        String str = String.format("FPS: %2d", fpsOverlayModel.getFps());
        g.drawString(str, 0, fm.getHeight());
    }

}
