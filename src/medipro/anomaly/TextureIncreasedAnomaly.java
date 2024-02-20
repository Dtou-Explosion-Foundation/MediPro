package medipro.anomaly;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.ornament.texture.TextureObjectModel;

/**
 * テクスチャが表示される数を増やしダミーを生成するコントローラ
 */
public class TextureIncreasedAnomaly extends GameObjectController implements AnomalyListener {
    /**
     * テクスチャが表示される数を増やしダミーを生成するコントローラの生成
     * @param model 対象のモデル
     */
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
