package medipro.object.player;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

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
     * 移動していない時のスプライトのインデックス.
     */
    int spritesIdleIndex = 1;

    /**
     * 現在のスプライトのインデックス.
     */
    int animationIndex = 0;
    String[] imagePaths = { "img/character/bear0.png", "img/character/bear1.png", "img/character/bear2.png", };
    int[] animations = { 0, 1, 2, 1 };

    /**
     * スプライトのアニメーションの最大切り替え時間. changeSpriteTimerがこの値を超えたらスプライトを切り替える。
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
     * 次のフレームで右に移動する. 実際に移動処理が行われるのは{@code updateMovement()}のタイミング. {@code direction}と{@code isWalking}を更新する.
     */
    public void moveRight() {
        direction = 1;
        isWalking = true;
    }

    /**
     * 次のフレームで左に移動する. 実際に移動処理が行われるのは{@code updateMovement()}のタイミング. {@code direction}と{@code isWalking}を更新する.
     */
    public void moveLeft() {
        direction = -1;
        isWalking = true;
    }

    public void update(double dt) {
        if (!updateAutoMover(dt))
            updateMovement(dt);
        updateAnimation(dt);
    }

    /**
     * 1フレーム分、アニメーションを更新する。 {@code changeSpriteTimer}を更新し、{@code changeSpriteTime}を元にスプライトを切り替える。 速度が考慮され、{@code speedX}が{@code speedLimitX}に近いほど素早くスプライトが切り替わる。
     * 
     * @param dt 前フレームからの経過時間
     */
    public void updateAnimation(double dt) {
        // update sprite animation
        changeSpriteTimer += dt;
        if (changeSpriteTimer > changeSpriteTime / (Math.abs(this.speedX) / this.speedLimitX)) {
            if (++animationIndex >= animations.length)
                animationIndex = 0;
            changeSpriteTimer = 0;
        }
    }

    /**
     * 1フレーム分、移動処理を行う。 スピードに加速度を加算し、位置を更新する。 また、スピードに抵抗を加算する。 さらに、スピードの上限を超えないようにする。
     * 
     * @param dt 前フレームからの経過時間
     */
    public void updateMovement(double dt) {
        // apply movement
        if (isWalking) {
            speedX += accX * direction * dt;
            isWalking = false;
        } else {
            animationIndex = spritesIdleIndex;
        }

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

        // update position
        x += speedX * dt;
    }

    public boolean updateAutoMover(double dt) {
        // logger.info("direction: " + direction);
        if (autoWalkerQueue.isEmpty()) {
            return false;
        }
        AutoWalker autoWalker = autoWalkerQueue.peek();
        autoWalker.update(dt);
        x = autoWalker.getNewX();
        y = autoWalker.getNewY();
        speedX = autoWalker.getSpeed();
        direction = autoWalker.getDirection();
        if (autoWalker.isFinished()) {
            autoWalkerQueue.poll();
            logger.info("updateAutoMover: remove head of queue " + autoWalkerQueue.size());
            autoWalker.invokeCallback();
        }
        return true;
    }

    private Queue<AutoWalker> autoWalkerQueue = new LinkedBlockingQueue<>();

    // public void pushAutoWalker(Point2D.Double target, double duration, Function<Double, Double> interpolation,
    //         Runnable callback) {
    //     autoWalkerQueue.add(new AutoWalker(target, duration, interpolation, callback));
    // }
    public void pushAutoWalker(AutoWalker autoWalker) {
        autoWalkerQueue.add(autoWalker);
        logger.info("pushAutoWalker: " + autoWalkerQueue.size());
        logger.info("New position: " + autoWalker.getNewX() + ", " + autoWalker.getNewY());
        x = autoWalker.getNewX();
        y = autoWalker.getNewY();
    }

    public boolean isPlayerAutoWalking() {
        return !autoWalkerQueue.isEmpty();
    }

}
