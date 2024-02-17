package medipro.object.stage.wall;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import medipro.object.base.camera.CameraModel;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

/**
 * 壁のビュー.
 */
public class WallView extends GameObjectView {

    /**
     * 壁のビューを生成する.
     * 
     * @param model 対象のモデル
     */
    public WallView(GameObjectModel model) {
        super(model);
    }

    @Override
    protected void draw(Graphics2D g) {
        WallModel model = (WallModel) this.model;
        if (model.imageWhite.isPresent()) {
            BufferedImage image = model.imageWhite.get();
            g.drawImage(image, -image.getWidth() / 2, -image.getHeight() / 2, null);
        }
        if (model.imageBlack.isPresent()) {
            BufferedImage image = model.imageBlack.get();
            double diff = 0;
            if (model.getWorld().getCamera().isPresent()) {
                CameraModel camera = model.getWorld().getCamera().get();
                diff = (camera.getX() - model.getX()) * camera.getScale() * -0.02;

            }
            AffineTransform transform = g.getTransform();
            g.translate(diff, 0);
            g.drawImage(image, -image.getWidth() / 2, -image.getHeight() / 2, null);
            g.setTransform(transform);

        }
    }

}
