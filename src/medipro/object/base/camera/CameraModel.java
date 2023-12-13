package medipro.object.base.camera;

import java.awt.geom.AffineTransform;

import medipro.config.InGameConfig;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

public class CameraModel extends GameObjectModel {

    public CameraModel(World world) {
        super(world);
    }

    public float scale = 1;

    @Override
    public AffineTransform getTransformMatrix() {
        AffineTransform transform = new AffineTransform();
        transform.translate(InGameConfig.WINDOW_WIDTH / 2, InGameConfig.WINDOW_HEIGHT / 2);
        transform.scale(scale, scale);
        transform.translate(-x, y);
        return transform;
    }

}
