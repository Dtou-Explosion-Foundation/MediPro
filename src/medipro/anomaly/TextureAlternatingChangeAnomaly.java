package medipro.anomaly;

import medipro.object.AnomalyListener;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.ornament.texture.TextureObjectModel;

public class TextureAlternatingChangeAnomaly extends GameObjectController implements AnomalyListener {

    public TextureAlternatingChangeAnomaly(GameObjectModel model) {
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

    @Override
    public int getOccurredChance() {
        if (model instanceof TextureObjectModel)
            return ((TextureObjectModel) model).getOccurredChance();
        return 0;
    }

    private double timer = 0;
    private double interval = 1;
    private int textureIndex;

    @Override
    public void update(double dt) {
        if (!isAnomalyOccurred)
            return;

        timer += dt;
        if (timer < interval)
            return;
        timer = 0;

        if (model instanceof TextureObjectModel) {
            TextureObjectModel textureObjectModel = (TextureObjectModel) model;
            textureIndex = (textureObjectModel.getTextureIndex() + 1) % textureObjectModel.getTexturePaths().length;
            if (textureIndex == 0)
                textureIndex++;
            logger.fine("Change Texture textureidnex:" + textureIndex);
            onAnomalyOccurred(textureIndex);
        }
    }

}
