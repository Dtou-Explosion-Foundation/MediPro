package medipro.anomaly;

import medipro.object.AnomalyListener;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.ornament.texture.TextureObjectModel;

/**
 * 静的にテクスチャが変化する異変.
 */
public class TextureChangeAnomaly extends GameObjectController implements AnomalyListener {

    /**
     * テクスチャが変化する異変を生成する.
     * 
     * @param model 対象のモデル
     */
    public TextureChangeAnomaly(GameObjectModel model) {
        super(model);
    }

    @Override
    public boolean canAnomalyOccurred() {
        return model instanceof TextureObjectModel;
    }

    @Override
    public void onAnomalyOccurred(int level) {
        // anomalyOccurred = true;
        if (model instanceof TextureObjectModel)
            ((TextureObjectModel) model).setTextureIndex(level);
    }

    @Override
    public void onAnomalyFinished() {
        if (model instanceof TextureObjectModel)
            ((TextureObjectModel) model).setTextureIndex(0);
    }

    @Override
    public int minAnomalyLevel() {
        return 1;
    }

    @Override
    public int maxAnomalyLevel() {
        if (model instanceof TextureObjectModel)
            return ((TextureObjectModel) model).getTexturePaths().length - 1;
        return 1;
    }

    @Override
    public int getOccurredChance() {
        if (model instanceof TextureObjectModel)
            return ((TextureObjectModel) model).getOccurredChance();
        return 0;
    }

    @Override
    public void update(double dt) {
    }

}
