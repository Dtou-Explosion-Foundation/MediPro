package medipro.object.base.camera;

import java.awt.geom.AffineTransform;

import medipro.config.Config;
import medipro.object.base.gameobject.GameObjectModel;

public class CameraModel extends GameObjectModel {

    public float scale = 1;

    @Override
    public void update(float dt) {
    }

    @Override
    public AffineTransform getTransformMatrix() {
        AffineTransform transform = new AffineTransform();
        transform.scale(scale, scale);
        transform.translate(-x, y);
        transform.translate(Config.WINDOW_WIDTH / 2 / scale, Config.WINDOW_HEIGHT / 2 / scale);
        return transform;
    }

}
