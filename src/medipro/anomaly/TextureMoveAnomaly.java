package medipro.anomaly;

import medipro.object.AnomalyListener;
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

    @Override
    public int getOccurredChance() {
        if (model instanceof TextureObjectModel)
            return ((TextureObjectModel) model).getOccurredChance();
        return 0;
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
                textureObjectModel.setDeltaX(model.deltaX += model.delta2X);
                model.addX(textureObjectModel.getDeltaX());
            }
            if (model.getY() > -200 || model.getY() < 350) {
                textureObjectModel.setDeltaY(model.deltaY += model.delta2Y);
                model.addY(textureObjectModel.getDeltaY());
            }
            ((TextureObjectModel) model).setTextureIndex(1);
        }
    }

}
