package medipro.object.camera;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import medipro.object.base.camera.CameraController;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * ターゲットを追跡するカメラのコントローラー.
 */
public class FollowingCameraController extends CameraController {

    /**
     * カメラコントローラを生成する.
     * 
     * @param models 格納するモデル
     */
    public FollowingCameraController(GameObjectModel... models) {
        super(models);
    }

    /**
     * {@inheritDoc}
     */
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

            Point2D.Double targetPos = new Point2D.Double();
            transform.transform(new Point2D.Double(_target.x, _target.y), targetPos);

            Point2D.Double cameraPos = new Point2D.Double();
            transform.transform(new Point2D.Double(_model.x, _model.y), cameraPos);

            Point2D.Double newCameraPos = new Point2D.Double(targetPos.x, targetPos.y);
            invertTransform.transform(newCameraPos, newCameraPos);

            _model.x = (int) newCameraPos.x + _model.originX;
            _model.y = (int) newCameraPos.y + _model.originY;
        }
    }
}
