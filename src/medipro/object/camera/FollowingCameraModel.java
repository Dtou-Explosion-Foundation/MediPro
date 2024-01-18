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
     * カメラのX座標を固定するかどうか.
     */
    private boolean lockX = false;
    /**
     * カメラのY座標を固定するかどうか.
     */
    private boolean lockY = false;

    /**
     * カメラのX座標の最小値.
     */
    private double minX = Double.NEGATIVE_INFINITY;
    /**
     * カメラのX座標の最大値.
     */
    private double maxX = Double.POSITIVE_INFINITY;
    /**
     * カメラのY座標の最小値.
     */
    private double minY = Double.NEGATIVE_INFINITY;
    /**
     * カメラのY座標の最大値.
     */
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

    /**
     * X座標が固定されているかどうかを返す.
     * 
     * @return X座標が固定されているかどうか
     */
    public boolean isLockX() {
        return lockX;
    }

    /**
     * X座標を固定するかどうかを設定する.
     * 
     * @param lockX X座標を固定するか
     */
    public void setLockX(boolean lockX) {
        this.lockX = lockX;
    }

    /**
     * Y座標が固定されているかどうかを返す.
     * 
     * @return Y座標を固定するか
     */
    public boolean isLockY() {
        return lockY;
    }

    /**
     * Y座標を固定するかどうかを設定する.
     * 
     * @param lockY Y座標を固定するか
     */
    public void setLockY(boolean lockY) {
        this.lockY = lockY;
    }

    /**
     * カメラのX座標の最小値を取得する.
     * 
     * @return カメラのX座標の最小値
     */
    public double getMinX() {
        return minX;
    }

    /**
     * カメラのX座標の最小値を設定する.
     * 
     * @param minX カメラのX座標の最小値
     */
    public void setMinX(double minX) {
        this.minX = minX;
    }

    /**
     * カメラのX座標の最大値を取得する.
     * 
     * @return カメラのX座標の最大値
     */
    public double getMaxX() {
        return maxX;
    }

    /**
     * カメラのX座標の最大値を設定する.
     * 
     * @param maxX カメラのX座標の最大値
     */
    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    /**
     * カメラのY座標の最小値を取得する.
     * 
     * @return カメラのY座標の最小値
     */
    public double getMinY() {
        return minY;
    }

    /**
     * カメラのY座標の最小値を設定する.
     * 
     * @param minY カメラのY座標の最小値
     */

    public void setMinY(double minY) {
        this.minY = minY;
    }

    /**
     * カメラのY座標の最大値を取得する.
     * 
     * @return カメラのY座標の最大値
     */
    public double getMaxY() {
        return maxY;
    }

    /**
     * カメラのY座標の最大値を設定する.
     * 
     * @param maxY カメラのY座標の最大値
     */
    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    /**
     * カメラの位置に制限を適用する.
     */
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
