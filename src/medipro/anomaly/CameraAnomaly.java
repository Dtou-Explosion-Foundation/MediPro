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

    private int level = -1;

    private boolean isAnomalyOccurred = false;

    @Override
    public void onAnomalyOccurred(int level) {
        this.level = level = 1;
        isAnomalyOccurred = true;
        switch (level) {
        case 0:
            // change zoom level
            // TODO: カメラのズームを変える異変の実装

            break;

        case 1:
            // 手ぶれ
            break;

        default:
            break;
        }
    }

    @Override
    public void onAnomalyFinished() {
        isAnomalyOccurred = false;
        switch (level) {
        case 0:
            // change zoom level

            break;

        case 1:
            // 手ぶれ
            break;

        default:
            break;
        }
    }

    @Override
    public int minAnomalyLevel() {
        return 1;
    }

    @Override
    public int maxAnomalyLevel() {
        return 1;
    }

    private int occurredChance = 1;

    public void setOccurredChance(int occurredChance) {
        this.occurredChance = occurredChance;
    }

    @Override
    public int getOccurredChance() {
        return occurredChance;
    }

    private double timer = 0;

    private Random random = new Random();

    private double prevDiffX = 0;
    private double prevDiffY = 0;

    private double diffRange = 10;

    @Override
    public void update(double dt) {
        if (!isAnomalyOccurred)
            return;
        switch (level) {
        case 1:
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
            break;

        default:
            break;
        }

    }

}
