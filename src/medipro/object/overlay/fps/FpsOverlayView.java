package medipro.object.overlay.fps;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

public class FpsOverlayView extends GameObjectView {

    public FpsOverlayView(GameObjectModel... models) {
        super(models);

    }

    @Override
    public void draw(GameObjectModel model, Graphics2D g) {
        g.setTransform(new AffineTransform());

        FpsOverlayModel fpsOverlayModel = (FpsOverlayModel) model;
        Font f = g.getFont();
        g.setFont(new Font(f.getName(), f.getStyle(), 32));
        FontMetrics fm = g.getFontMetrics();
        String str = String.format("FPS: %2d", fpsOverlayModel.getFps());
        g.drawString(str, fm.stringWidth(str) / 2, fm.getHeight());
    }
}
