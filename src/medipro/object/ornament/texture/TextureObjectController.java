package medipro.object.ornament.texture;

import medipro.object.AnomalyListener;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * テクスチャのコントローラ.
 */
public class TextureObjectController extends GameObjectController implements AnomalyListener {
    /**
     * テクスチャのコントローラを生成する.
     * 
     * @param model 対象のモデル
     */
    public TextureObjectController(GameObjectModel model) {
        super(model);
    }

    @Override
    public void update(double dt) {

    }

    /**
     * 異変が発生しているかどうか.
     */
    private static boolean anomalyOccurred = false;

    @Override
    public boolean canAnomalyOccurred() {
        return !anomalyOccurred;
    }

    @Override
    public void onAnomalyOccurred(int level) {
        anomalyOccurred = true;
        ((TextureObjectModel) model).setTextureIndex(level);
    }

    @Override
    public void onAnomalyFinished() {
        ((TextureObjectModel) model).setTextureIndex(0);
    }

    @Override
    public int minAnomalyLevel() {
        return 0;
    }

    @Override
    public int maxAnomalyLevel() {
        return 1;
    }

    @Override
    public int getOccurredChance() {
        return 1;
    }
}
