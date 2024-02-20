package medipro.anomaly;

import java.util.Random;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.camera.FollowingCameraModel;

/**
 * カメラの手ぶれを発生させる異変.
 */
public class CameraAnomaly extends GameObjectController implements AnomalyListener {

    /**
     * CameraAnomalyを生成する.
     * 
     * @param model 対象のモデル
     */
    public CameraAnomaly(GameObjectModel model) {
        super(model);
    }

    @Override
    public boolean canAnomalyOccurred() {
        return true;
    }

    /**
     * 異常が発生しているか.
     */
    private boolean isAnomalyOccurred = false;

    @Override
    public void onAnomalyOccurred(int level) {
        isAnomalyOccurred = true;
    }

    @Override
    public void onAnomalyFinished() {
        isAnomalyOccurred = false;
    }

    @Override
    public int minAnomalyLevel() {
        return 0;
    }

    @Override
    public int maxAnomalyLevel() {
        return 0;
    }

    /**
     * 発生確率.
     */
    private int occurredChance = 1;

    /**
     * 発生確率を設定する.
     * 
     * @param chance 発生確率
     */
    public void setOccurredChance(int chance) {
        this.occurredChance = chance;
    }

    @Override
    public int getOccurredChance() {
        return occurredChance;
    }

    /**
     * 手振れ用のタイマー.
     */
    private double timer = 0;

    /**
     * 乱数生成用クラス.
     */
    private Random random = new Random();

    /**
     * 前回の手ぶれ量.
     */
    private double prevDiffX = 0;
    /**
     * 前回の手ぶれ量.
     */
    private double prevDiffY = 0;

    /**
     * 手ぶれの範囲.
     */
    private static final double DIFF_RANGE = 10;

    /**
     * 手ぶれのインターバル.
     */
    private static final double INTERVAL = 0.2;

    @Override
    public void update(double dt) {
        if (!isAnomalyOccurred)
            return;

        timer += dt;
        if (timer < INTERVAL)
            return;
        timer = 0;

        if (model instanceof FollowingCameraModel) {
            FollowingCameraModel cameraModel = (FollowingCameraModel) model;
            cameraModel.originX -= prevDiffX;
            cameraModel.originY -= prevDiffY;
            prevDiffX = random.nextDouble() * DIFF_RANGE - DIFF_RANGE / 2;
            prevDiffY = random.nextDouble() * DIFF_RANGE - DIFF_RANGE / 2;
            cameraModel.originX += prevDiffX;
            cameraModel.originY += prevDiffY;
        }
    }
}
