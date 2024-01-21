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
        if (model.image_white.isPresent()) {
            BufferedImage image = model.image_white.get();
            g.drawImage(image, -image.getWidth() / 2, -image.getHeight() / 2, null);
        }
        if (model.image_black.isPresent()) {
            BufferedImage image = model.image_black.get();
            double diff = 0;
            if (model.world.camera.isPresent()) {
                CameraModel camera = model.world.camera.get();
                diff = (camera.x - model.x) * camera.getScale() * -0.02;

            }
            AffineTransform transform = g.getTransform();
            g.translate(diff, 0);
            g.drawImage(image, -image.getWidth() / 2, -image.getHeight() / 2, null);
            g.setTransform(transform);

        }
    }

}
