package medipro.anomaly;

import medipro.object.AnomalyListener;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.ornament.texture.TextureObjectModel;

public class TextureIncreasedAnomaly extends GameObjectController implements AnomalyListener {

    public TextureIncreasedAnomaly(GameObjectModel model) {
        super(model);
    }

    @Override
    public boolean canAnomalyOccurred() {
        return true;
    }

    @Override
    public void onAnomalyOccurred(int level) {
        if (model instanceof TextureObjectModel)
            ((TextureObjectModel) model).isDummies = true;
    }

    @Override
    public void onAnomalyFinished() {
        if (model instanceof TextureObjectModel)
            ((TextureObjectModel) model).isDummies = false;
    }

    @Override
    public int minAnomalyLevel() {
        return 0;
    }

    @Override
    public int maxAnomalyLevel() {
        return 0;
    }

    private int occurredChance = 1;

    public void setOccurredChance(int chance) {
        occurredChance = chance;
    }

    @Override
    public int getOccurredChance() {
        return occurredChance;
    }

    @Override
    public void update(double dt) {

    }

}
