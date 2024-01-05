package medipro.object.ornament.texture;

import medipro.object.AnomalyListener;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

public class TextureController extends GameObjectController implements AnomalyListener {

    public TextureController(GameObjectModel model) {
        super(model);
    }

    @Override
    public void update(double dt) {

    }

    private boolean anomalyOccurred;

    @Override
    public boolean canAnomalyOccurred() {
        return !anomalyOccurred;
    }

    @Override
    public void onAnomalyOccurred(int level) {
        anomalyOccurred = true;
        ((TextureModel) model).setTextureIndex(clampAnomalyLevel(level));
    }

    @Override
    public void onAnomalyFinished() {
        anomalyOccurred = false;
        ((TextureModel) model).setTextureIndex(0);
    }

    private int clampAnomalyLevel(int level) {
        return Math.max(minAnomalyLevel(), Math.min(maxAnomalyLevel(), level));
    }

    @Override
    public int minAnomalyLevel() {
        return 1;
    }

    @Override
    public int maxAnomalyLevel() {
        return 0;
    }

    @Override
    public int getOccurredChance() {
        return 1;
    }
}
