package medipro.object.camera;

import java.awt.geom.Point2D;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.player.PlayerModel;

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

    // 描画上のorigin
    private double _originX = 0;
    private double _originY = 0;

    /**
     * setupWorld()が実行された後に呼ばれる.
     */
    @Override
    public void postSetupWorld() {
        SmoothFollowingCameraModel model = (SmoothFollowingCameraModel) this.model;
        _originX = model.originX;
        _originY = model.originY;
        this.forceFollow();
    }

    private double clamp(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    private void updateOrigin(double dt) {
        SmoothFollowingCameraModel model = (SmoothFollowingCameraModel) this.model;
        if (!model.target.isPresent())
            return;
        GameObjectModel target = model.target.get();
        if (!(target instanceof PlayerModel))
            return;
        PlayerModel player = (PlayerModel) target;
        double newOriginX = model.originX;
        if (model.isFlipOriginXWithPlayerDirection())
            newOriginX *= player.getDirection();
        double newOriginY = model.originY;

        // 線形で_originを更新する
        double speed = model.getFlipSpeed() * dt;
        if (Math.abs(_originX - newOriginX) < speed)
            _originX = newOriginX;
        else
            _originX += Math.signum(newOriginX - _originX) * speed;

        if (Math.abs(_originY - newOriginY) < speed)
            _originY = newOriginY;
        else
            _originY += Math.signum(newOriginY - _originY) * speed;

    }

    /**
     * モデルを次フレームの状態に更新する. カメラをターゲットの位置に近づけるようにカメラの位置を更新する. カメラ座標に変換した上で比較するので、ズームや回転に対応している.
     * 
     * @param dt 前フレームからの経過時間
     */
    @Override
    public void update(double dt) {
        updateOrigin(dt);
        SmoothFollowingCameraModel _model = (SmoothFollowingCameraModel) model;
        if (_model.target.isPresent()) {
            GameObjectModel _target = _model.target.get();
            // originを考慮したカメラの位置
            double _cameraX = _model.x - _originX;
            double _cameraY = (_model.y - _originY) / _model.getFollowingRateY();
            // dtを考慮したカメラの移動速度
            double _followingSpeed = _model.followingSpeed * dt;
            // カメラの移動範囲を考慮したターゲットの位置
            double _targetX = clamp(_target.x, _model.getMinX() - _originX, _model.getMaxX() - _originX);
            double _targetY = clamp(_target.y, _model.getMinY() - _originY, _model.getMaxY() - _originY);

            Point2D.Double newCameraPos = new Point2D.Double(
                    _targetX * _followingSpeed + _cameraX * (1 - _followingSpeed),
                    _targetY * _followingSpeed + _cameraY * (1 - _followingSpeed));

            if (!_model.isLockX())
                _model.x = newCameraPos.x + _originX;
            if (!_model.isLockY())
                _model.y = newCameraPos.y * _model.getFollowingRateY() + _originY;
            _model.clampPosition();
        }
    }
}
