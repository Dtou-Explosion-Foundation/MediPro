package medipro.object.camera;

import java.util.Optional;

import medipro.object.base.World;
import medipro.object.base.camera.CameraModel;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * ターゲットを追跡するカメラのモデル.
 */
public class FollowingCameraModel extends CameraModel {

    /**
     * 追跡する対象のオブジェクト.
     */
    public Optional<GameObjectModel> target = Optional.empty();

    /**
     * ターゲットからからoriginXだけずらした位置にカメラを移動する.
     */
    public double originX = 0;
    /**
     * ターゲットからからoriginYだけずらした位置にカメラを移動する.
     */
    public double originY = 0;

    /**
     * カメラモデルを生成する.
     * 
     * @param world  モデルが存在するワールド
     * @param target 追跡する対象のオブジェクト
     */
    public FollowingCameraModel(World world, GameObjectModel target) {
        super(world);
        this.target = Optional.ofNullable(target);
    }
}
