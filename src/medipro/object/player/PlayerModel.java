package medipro.object.player;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

/**
 * プレイヤーのモデル.
 */
public class PlayerModel extends GameObjectModel {

    // movement
    /**
     * 現在の移動速度.
     */
    double speedX = 0;
    /**
     * 最大移動速度.
     */
    public double speedLimitX = 100;
    /**
     * 減速度.
     */
    public double resitX = 600;
    /**
     * 加速度.
     */
    public double accX = 1200;

    // sprite animation
    /**
     * 待機時のスプライトのインデックス.
     */
    int spritesIdleIndex = 1;
    /**
     * 現在のスプライトのインデックス.
     */
    int spritesIndex = 0;
    /**
     * スプライトのアニメーションの範囲.
     */
    int[] spritesRange = { 0, 3 };
    /**
     * スプライトのアニメーションの最大切り替え時間.
     */
    final static float changeSpriteTime = 0.15f;
    /**
     * 向いている方向.
     */
    byte direction = 1;
    /**
     * 歩いているかどうか.
     */
    Boolean isWalking = false;
    /**
     * スプライトの切り替え時間を計測するタイマー.
     */
    float changeSpriteTimer = 0;

    /**
     * プレイヤーのモデルを生成する.
     * 
     * @param world オブジェクトが存在するワールド
     */
    public PlayerModel(World world) {
        super(world);
        logger.info("PlayerModel created");
    }

    /**
     * 解放時の処理 ログ出力用
     * 
     * @throws Throwable 例外
     */
    @Override
    protected void finalize() throws Throwable {
        logger.info("PlayerModel destroyed");
    }

    /**
     * 右に移動する。実際に移動処理が行われるのはupdateMovement()のタイミング。
     */
    public void moveRight() {
        direction = 1;
        isWalking = true;
    }

    /**
     * 左に移動する。実際に移動処理が行われるのはupdateMovement()のタイミング。
     */
    public void moveLeft() {
        direction = -1;
        isWalking = true;
    }

    /**
     * 1フレーム分、アニメーションを更新する。
     * 
     * @param dt 前フレームからの経過時間
     */
    public void updateAnimation(float dt) {
        // update sprite animation
        changeSpriteTimer += dt;
        if (changeSpriteTimer > changeSpriteTime / (Math.abs(this.speedX) / this.speedLimitX)) {
            if (++spritesIndex > spritesRange[1])
                spritesIndex = spritesRange[0];
            changeSpriteTimer = 0;
        }
    }

    /**
     * 1フレーム分、移動処理を行う。
     * 
     * @param dt 前フレームからの経過時間
     */
    public void updateMovement(float dt) {
        // apply movement
        if (isWalking) {
            speedX += accX * direction * dt;
            isWalking = false;
        } else {
            spritesIndex = spritesIdleIndex;
        }

        // update position
        x += speedX * dt;

        // apply resistance
        if (speedX > 0) {
            speedX -= resitX * dt;
            if (speedX < 0)
                speedX = 0;
        } else if (speedX < 0) {
            speedX += resitX * dt;
            if (speedX > 0)
                speedX = 0;
        }

        // apply speed limit
        if (speedX > speedLimitX)
            speedX = speedLimitX;
        else if (speedX < -speedLimitX)
            speedX = -speedLimitX;
    }
}
