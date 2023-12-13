package medipro.object.camera;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Optional;

import medipro.object.base.camera.CameraModel;
import medipro.object.base.gameobject.GameObjectModel;

public class FollowingCameraModel extends CameraModel {

    public Optional<GameObjectModel> target = Optional.empty();

    public int originX = 0;
    public int originY = 0;

    public FollowingCameraModel(GameObjectModel target) {
        super();
        this.target = Optional.of(target);
    }

    @Override
    public void update(float dt) {
        if (target.isPresent()) {
            GameObjectModel _target = target.get();
            AffineTransform transform = this.getTransformMatrix();
            AffineTransform invertTransform;
            try {
                invertTransform = transform.createInverse();
            } catch (NoninvertibleTransformException e) {
                logger.warning("NoninvertibleTransformException");
                invertTransform = new AffineTransform();
            }

            Point2D.Float targetPos = new Point2D.Float();
            transform.transform(new Point2D.Float(_target.x, _target.y), targetPos);

            Point2D.Float cameraPos = new Point2D.Float();
            transform.transform(new Point2D.Float(this.x, this.y), cameraPos);

            Point2D.Float newCameraPos = new Point2D.Float(
                    targetPos.x, targetPos.y);
            invertTransform.transform(newCameraPos, newCameraPos);

            this.x = (int) newCameraPos.x + originX;
            this.y = (int) newCameraPos.y + originY;
        }
    }
}
