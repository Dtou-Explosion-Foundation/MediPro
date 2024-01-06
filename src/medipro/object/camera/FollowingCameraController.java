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

    @Override
    public void postSetupWorld() {
        FollowingCameraModel followingCameraModel = (FollowingCameraModel) model;
        if (followingCameraModel.target.isPresent()) {
            GameObjectModel _target = followingCameraModel.target.get();
            followingCameraModel.x = _target.x + followingCameraModel.originX;
            followingCameraModel.y = _target.y + followingCameraModel.originY;
        }
        followingCameraModel.clampPosition();
    }

    /**
     * モデルを次フレームの状態に更新する. カメラをターゲットの位置と同じ位置にカメラの位置を更新する.
     * 
     * @param model 更新対象のモデル
     * @param dt    前フレームからの経過時間
     */
    @Override
    public void update(double dt) {
        FollowingCameraModel followingCameraModel = (FollowingCameraModel) model;
        if (followingCameraModel.target.isPresent()) {
            GameObjectModel _target = followingCameraModel.target.get();
            if (!followingCameraModel.isLockX())
                followingCameraModel.x = _target.x + followingCameraModel.originX;
            if (!followingCameraModel.isLockY())
                followingCameraModel.y = _target.y + followingCameraModel.originY;
            followingCameraModel.clampPosition();
        }
    }
}
