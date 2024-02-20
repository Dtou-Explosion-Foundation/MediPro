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

    // 
    /**
     * 描画上のorigin. model.originXを数フレームかけて追従する.
     */
    private double originX = 0;
    /**
     * 描画上のorigin. model.originYを数フレームかけて追従する.
     */
    private double originY = 0;

    /**
     * setupWorld()が実行された後に呼ばれる.
     */
    @Override
    public void postSetupWorld() {
        SmoothFollowingCameraModel model = (SmoothFollowingCameraModel) this.model;
        originX = model.originX;
        originY = model.originY;
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

        // 線形でoriginを更新する
        double speed = model.getFlipSpeed() * dt;
        if (Math.abs(originX - newOriginX) < speed)
            originX = newOriginX;
        else
            originX += Math.signum(newOriginX - originX) * speed;

        if (Math.abs(originY - newOriginY) < speed)
            originY = newOriginY;
        else
            originY += Math.signum(newOriginY - originY) * speed;

    }

    /**
     * モデルを次フレームの状態に更新する. カメラをターゲットの位置に近づけるようにカメラの位置を更新する. カメラ座標に変換した上で比較するので、ズームや回転に対応している.
     * 
     * @param dt 前フレームからの経過時間
     */
    @Override
    public void update(double dt) {
        updateOrigin(dt);
        SmoothFollowingCameraModel model = (SmoothFollowingCameraModel) this.model;
        if (model.target.isPresent()) {
            GameObjectModel target = model.target.get();
            // originを考慮したカメラの位置
            double cameraX = model.getX() - originX;
            double cameraY = (model.getY() - originY) / model.getFollowingRateY();
            // dtを考慮したカメラの移動速度
            double followingSpeed = model.followingSpeed * dt;
            // カメラの移動範囲を考慮したターゲットの位置
            double targetX = clamp(target.getX(), model.getMinX() - originX, model.getMaxX() - originX);
            double targetY = clamp(target.getY(), model.getMinY() - originY, model.getMaxY() - originY);

            Point2D.Double newCameraPos = new Point2D.Double(targetX * followingSpeed + cameraX * (1 - followingSpeed),
                    targetY * followingSpeed + cameraY * (1 - followingSpeed));

            if (!model.isLockX())
                model.setX(newCameraPos.x + originX);
            if (!model.isLockY())
                model.setY(newCameraPos.y * model.getFollowingRateY() + originY);
            model.clampPosition();
        }
    }
}
