package medipro.object.overlay.floor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import medipro.config.InGameConfig;
import medipro.object.base.gameobject.GameObjectView;
import medipro.object.manager.gamemanager.GameManagerModel;

public class FloorOverlayView extends GameObjectView {
    public FloorOverlayView(FloorOverlayModel model) {
        super(model);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setTransform(new AffineTransform());
        int floor = GameManagerModel.getFloor();
        g.setFont(new Font("SansSerif", Font.BOLD, 75));
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(floor) + "層", InGameConfig.WINDOW_WIDTH - 200, 175);
        g.setFont(new Font("SansSerif", Font.BOLD, 50));
        g.drawString("現在", InGameConfig.WINDOW_WIDTH - 300, 100);
    }
}
