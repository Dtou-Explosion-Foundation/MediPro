package medipro.object.camera;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * ターゲットをスムーズに追跡するカメラのモデル.
 */
public class SmoothFollowingCameraModel extends FollowingCameraModel {
    /**
     * ターゲットを追跡する際のスピード. 最大スピードに対する割合で指定する.
     */
    public Double followingSpeed = 0.1;

    /**
     * OriginXをプレイヤーの向きに合わせて反転するかどうか.
     */
    private boolean isFlipOriginXWithPlayerDirection = true;

    /**
     * OriginXをプレイヤーの向きに合わせて反転するかどうかを取得する.
     * 
     * @return OriginXをプレイヤーの向きに合わせて反転するかどうか
     */
    public boolean isFlipOriginXWithPlayerDirection() {
        return isFlipOriginXWithPlayerDirection;
    }

    /**
     * OriginXをプレイヤーの向きに合わせて反転するかどうかを設定する.
     * 
     * @param isFlipOriginXWithPlayerDirection OriginXをプレイヤーの向きに合わせて反転するかどうか
     */
    public void setFlipOriginXWithPlayerDirection(boolean isFlipOriginXWithPlayerDirection) {
        this.isFlipOriginXWithPlayerDirection = isFlipOriginXWithPlayerDirection;
    }

    /**
     * OriginXを反転するときのスピード.
     */
    private double flipSpeed = 50;

    /**
     * OriginXを反転するときのスピードを取得する.
     * 
     * @return OriginXを反転するときのスピード
     */
    public double getFlipSpeed() {
        return flipSpeed;
    }

    /**
     * OriginXを反転するときのスピードを設定する.
     * 
     * @param flipSpeed OriginXを反転するときのスピード
     */
    public void setFlipSpeed(double flipSpeed) {
        this.flipSpeed = flipSpeed;
    }

    /**
     * カメラモデルを生成する.
     * 
     * @param world  モデルが存在するワールド
     * @param target 追跡する対象のオブジェクト
     */
    public SmoothFollowingCameraModel(World world, GameObjectModel target) {
        super(world, target);
    }
}
