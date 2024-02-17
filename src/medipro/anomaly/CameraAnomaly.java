package medipro.anomaly;

import java.util.Random;

import medipro.object.AnomalyListener;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.camera.FollowingCameraModel;

public class CameraAnomaly extends GameObjectController implements AnomalyListener {

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
     * @param occurredChance 発生確率
     */
    public void setOccurredChance(int occurredChance) {
        this.occurredChance = occurredChance;
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
    private double diffRange = 10;

    @Override
    public void update(double dt) {
        if (!isAnomalyOccurred)
            return;

        timer += dt;
        if (timer < 0.2)
            return;
        timer = 0;

        if (model instanceof FollowingCameraModel) {
            FollowingCameraModel cameraModel = (FollowingCameraModel) model;
            cameraModel.originX -= prevDiffX;
            cameraModel.originY -= prevDiffY;
            prevDiffX = random.nextDouble() * diffRange - diffRange / 2;
            prevDiffY = random.nextDouble() * diffRange - diffRange / 2;
            cameraModel.originX += prevDiffX;
            cameraModel.originY += prevDiffY;
        }
    }
}
