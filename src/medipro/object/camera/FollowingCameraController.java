package medipro.object.camera;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import medipro.object.base.camera.CameraController;
import medipro.object.base.gameobject.GameObjectModel;

public class FollowingCameraController extends CameraController {

    public FollowingCameraController(GameObjectModel... models) {
        super(models);
    }

    @Override
    public void update(GameObjectModel model, float dt) {
        FollowingCameraModel _model = (FollowingCameraModel) model;
        if (_model.target.isPresent()) {
            GameObjectModel _target = _model.target.get();
            AffineTransform transform = _model.getTransformMatrix();
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
            transform.transform(new Point2D.Float(_model.x, _model.y), cameraPos);

            Point2D.Float newCameraPos = new Point2D.Float(
                    targetPos.x, targetPos.y);
            invertTransform.transform(newCameraPos, newCameraPos);

            _model.x = (int) newCameraPos.x + _model.originX;
            _model.y = (int) newCameraPos.y + _model.originY;
        }
    }
}