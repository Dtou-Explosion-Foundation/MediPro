package medipro.object.overlay.vignette;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import medipro.config.InGameConfig;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

/**
 * ビネットのビュー.
 */
public class VignetteView extends GameObjectView {

    /**
     * ビネットのビューを生成する.
     * 
     * @param model 対象のモデル
     */
    public VignetteView(GameObjectModel model) {
        super(model);
    }

    @Override
    protected void draw(Graphics2D g) {
        VignetteModel vignetteModel = (VignetteModel) this.model;
        double windowWidth = InGameConfig.WINDOW_WIDTH * model.getWorld().getPanel().getFrame().getScreenScaleFactor();
        double windowHeight = InGameConfig.WINDOW_HEIGHT
                * model.getWorld().getPanel().getFrame().getScreenScaleFactor();
        if (vignetteModel.getTexture().isPresent()) {
            BufferedImage image = vignetteModel.getTexture().get();
            g.setTransform(
                    AffineTransform.getScaleInstance(windowWidth / image.getWidth(), windowHeight / image.getHeight()));
            g.drawImage(image, 0, 0, null);
        }

    }

}
