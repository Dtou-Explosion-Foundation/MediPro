package medipro.anomaly;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.ornament.texture.TextureObjectModel;

public class TextureMoveAnomaly extends GameObjectController implements AnomalyListener {

    public TextureMoveAnomaly(GameObjectModel model) {
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
            if (model.getX() < 650 || model.getX() > -650) {
                textureObjectModel.setDeltaX(textureObjectModel.deltaX += textureObjectModel.delta2X);
                model.addX(textureObjectModel.getDeltaX());
            }
            if (model.getY() > -200 || model.getY() < 350) {
                textureObjectModel.setDeltaY(textureObjectModel.deltaY += textureObjectModel.delta2Y);
                model.addY(textureObjectModel.getDeltaY());
            }
            ((TextureObjectModel) model).setTextureIndex(1);
        }
    }

}
