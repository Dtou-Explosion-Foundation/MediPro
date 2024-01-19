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

    private boolean lockX = false;
    private boolean lockY = false;

    private double minX = Double.NEGATIVE_INFINITY;
    private double maxX = Double.POSITIVE_INFINITY;
    private double minY = Double.NEGATIVE_INFINITY;
    private double maxY = Double.POSITIVE_INFINITY;

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

    public boolean isLockX() {
        return lockX;
    }

    public void setLockX(boolean lockX) {
        this.lockX = lockX;
    }

    public boolean isLockY() {
        return lockY;
    }

    public void setLockY(boolean lockY) {
        this.lockY = lockY;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public void clampPosition() {
        if (x < minX)
            x = minX;
        if (x > maxX)
            x = maxX;
        if (y < minY)
            y = minY;
        if (y > maxY)
            y = maxY;
    }

}
