package medipro.object.camera;

import java.awt.geom.Point2D;

import medipro.object.base.gameobject.GameObjectModel;

/**
 * ターゲットをスムーズに追跡するカメラのコントローラー.
 */
public class SmoothFollowingCameraController extends FollowingCameraController {

    /**
     * カメラコントローラを生成する.
     * 
     * @param model 対象のモデル
     */
    public SmoothFollowingCameraController(GameObjectModel model) {
        super(model);
    }

    /**
     * モデルを次フレームの状態に更新する. カメラをターゲットの位置に近づけるようにカメラの位置を更新する. カメラ座標に変換した上で比較するので、ズームや回転に対応している.
     * 
     * @param dt 前フレームからの経過時間
     */
    @Override
    public void update(double dt) {
        // TODO:dtを使ってFPSに依存しないようにする
        SmoothFollowingCameraModel _model = (SmoothFollowingCameraModel) model;
        if (_model.target.isPresent()) {
            GameObjectModel _target = _model.target.get();
            Point2D.Double newCameraPos = new Point2D.Double(
                    _target.x * _model.followingSpeed + (_model.x - _model.originX) * (1 - _model.followingSpeed),
                    _target.y * _model.followingSpeed + (_model.y - _model.originY) * (1 - _model.followingSpeed));

            if (!_model.isLockX())
                _model.x = newCameraPos.x + _model.originX;
            if (!_model.isLockY())
                _model.y = newCameraPos.y * _model.getFollowingRateY() + _model.originY;
            _model.clampPosition();
        }
    }
}
