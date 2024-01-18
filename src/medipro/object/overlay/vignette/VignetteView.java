package medipro.object.overlay.vignette;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import medipro.config.InGameConfig;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

public class VignetteView extends GameObjectView {

    public VignetteView(GameObjectModel model) {
        super(model);
    }

    @Override
    protected void draw(Graphics2D g) {
        VignetteModel vignetteModel = (VignetteModel) this.model;
        if (vignetteModel.image.isPresent()) {
            BufferedImage image = vignetteModel.image.get();
            // g.setTransform(new AffineTransform());
            g.setTransform(AffineTransform.getScaleInstance((double) InGameConfig.WINDOW_WIDTH / image.getWidth(),
                    (double) InGameConfig.WINDOW_HEIGHT / image.getHeight()));
            // g.setTransform(AffineTransform.getScaleInstance(InGameConfig.WINDOW_WIDTH / image.getWidth(),
            //         InGameConfig.WINDOW_HEIGHT / image.getHeight()));
            g.drawImage(image, 0, 0, null);
        }

    }

    @Override
    protected boolean needUpdateTexture() {
        return false;
    }
}
