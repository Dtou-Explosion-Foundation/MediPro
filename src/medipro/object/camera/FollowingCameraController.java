package medipro.object.camera;

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
    public FollowingCameraController(GameObjectModel model) {
        super(model);
    }

    /**
     * モデルを次フレームの状態に更新する. カメラをターゲットの位置と同じ位置にカメラの位置を更新する.
     * 
     * @param model 更新対象のモデル
     * @param dt    前フレームからの経過時間
     */
    @Override
    public void update(double dt) {
        FollowingCameraModel _model = (FollowingCameraModel) model;
        if (_model.target.isPresent()) {
            GameObjectModel _target = _model.target.get();
            _model.x = _target.x + _model.originX;
            _model.y = _target.y + _model.originY;
        }
    }
}
