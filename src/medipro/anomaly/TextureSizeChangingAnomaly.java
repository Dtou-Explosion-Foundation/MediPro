package medipro.anomaly;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.ornament.texture.TextureObjectModel;
/**
 * テクスチャのサイズを変更する異変のコントローラー
 */
public class TextureSizeChangingAnomaly extends GameObjectController implements AnomalyListener {
    /**
     * テクスチャのサイズを変更する異変のコントローラーの生成
     * @param model 対象のモデル
     */
    public TextureSizeChangingAnomaly(GameObjectModel model) {
        super(model);
    }

    @Override
    public boolean canAnomalyOccurred() {
        return model instanceof TextureObjectModel;
    }

    private boolean isAnomalyOccurred = false;

    @Override
    public void onAnomalyOccurred(int level) {
        isAnomalyOccurred = true;
        if (model instanceof TextureObjectModel)
            ((TextureObjectModel) model).setTextureIndex(level);
    }

    @Override
    public void onAnomalyFinished() {
        isAnomalyOccurred = false;
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

    private double timer = 0;

    @Override
    public void update(double dt) {
        if (!isAnomalyOccurred)
            return;
        TextureObjectModel textureObjectModel = (TextureObjectModel) model;
        timer += dt;
        if (timer < textureObjectModel.getInterval())
            return;
        timer = 0;

        if (model instanceof TextureObjectModel) {
            if (model.getScaleX() < 50) {
                textureObjectModel.setDeltaX(textureObjectModel.deltaX += textureObjectModel.delta2X);
                model.addScaleX(textureObjectModel.getDeltaX());
            }
            if (model.getScaleY() < 50) {
                textureObjectModel.setDeltaY(textureObjectModel.deltaY += textureObjectModel.delta2Y);
                model.addScaleY(textureObjectModel.getDeltaY());
            }
            ((TextureObjectModel) model).setTextureIndex(1);
        }
    }

}
