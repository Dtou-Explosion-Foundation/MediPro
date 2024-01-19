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
     * @param model 対象のモデル
     */
    public FollowingCameraController(GameObjectModel model) {
        super(model);
    }

    @Override
    public void postSetupWorld() {
        FollowingCameraModel followingCameraModel = (FollowingCameraModel) model;
        if (followingCameraModel.target.isPresent()) {
            GameObjectModel _target = followingCameraModel.target.get();
            if (!followingCameraModel.isLockX())
                followingCameraModel.x = _target.x + followingCameraModel.originX;
            if (!followingCameraModel.isLockY())
                followingCameraModel.y = _target.y * followingCameraModel.getFollowingRateY()
                        + followingCameraModel.originY;
        }
        followingCameraModel.clampPosition();
    }

    /**
     * モデルを次フレームの状態に更新する. カメラをターゲットの位置と同じ位置にカメラの位置を更新する.
     * 
     * @param dt 前フレームからの経過時間
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

    /**
     * カメラのY座標をターゲットのY座標に強制的に更新する.LockXを考慮しない.
     */
    public void forceFollowX() {
        FollowingCameraModel followingCameraModel = (FollowingCameraModel) model;
        if (followingCameraModel.target.isPresent()) {
            GameObjectModel _target = followingCameraModel.target.get();
            followingCameraModel.x = _target.x + followingCameraModel.originX;
            followingCameraModel.clampPosition();
        }
    }

    /**
     * カメラのY座標をターゲットのY座標に強制的に更新する.LockYを考慮しない.
     */
    public void forceFollowY() {
        FollowingCameraModel followingCameraModel = (FollowingCameraModel) model;
        if (followingCameraModel.target.isPresent()) {
            GameObjectModel _target = followingCameraModel.target.get();
            followingCameraModel.y = _target.y + followingCameraModel.originY;
            followingCameraModel.clampPosition();
        }
    }

    /**
     * カメラの座標をターゲットの座標に強制的に更新する.LockX,LockYを考慮しない.
     */
    public void forceFollow() {
        forceFollowX();
        forceFollowY();
    }
}
