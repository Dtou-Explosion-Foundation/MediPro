package medipro.object.camera;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import medipro.object.base.camera.CameraController;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * ターゲットをスムーズに追跡するカメラのコントローラー.
 */
public class SmoothFollowingCameraController extends CameraController {

    /**
     * カメラコントローラを生成する.
     * 
     * @param models 格納するモデル
     */
    public SmoothFollowingCameraController(GameObjectModel... models) {
        super(models);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(GameObjectModel model, float dt) {
        SmoothFollowingCameraModel _model = (SmoothFollowingCameraModel) model;
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

            Point2D.Double newCameraPos = new Point2D.Double(
                    (targetPos.x - cameraPos.x) * _model.followingSpeed + cameraPos.x,
                    (targetPos.y - cameraPos.y) * _model.followingSpeed + cameraPos.y);
            invertTransform.transform(newCameraPos, newCameraPos);

            _model.x = newCameraPos.x + _model.originX;
            _model.y = newCameraPos.y + _model.originY;
        }
    }
}
