package medipro.object.ornament.texture;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.NoSuchElementException;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;
import medipro.util.ImageUtil;

/**
 * テクスチャを主としたオブジェクトのビュー.
 */
public class TextureObjectView extends GameObjectView {
    /**
     * テクスチャ
     */
    private Image[] textures = null;

    /**
     * TextureViewを生成する.
     * 
     * @param model 対象のモデル
     */
    public TextureObjectView(GameObjectModel model) {
        super(model);
        TextureObjectModel textureModel = (TextureObjectModel) model;
        String[] texturePaths = textureModel.getTexturePaths();
        if (texturePaths != null) {
            textures = new Image[texturePaths.length];
            for (int i = 0; i < texturePaths.length; i++) {
                try {
                    // textures[i] = ImageIO.read(new File(texturePaths[i]));
                    textures[i] = ImageUtil.loadImage(texturePaths[i]).get();
                } catch (NoSuchElementException e) {
                    // logger.warning("Failed to load texture: " + texturePaths[i]);
                }
            }
        }
    }

    @Override
    protected void draw(Graphics2D g) {
        TextureObjectModel textureModel = (TextureObjectModel) model;
        int textureIndex = textureModel.getTextureIndex();
        if (textures != null && textureIndex >= 0 && textureIndex < textures.length) {
            if (textureModel.hasDummies()) {
                for (int i = -model.timesX; i <= model.timesX; i++) {
                    for (int j = -model.timesY; j <= model.timesY; j++) {
                        g.drawImage(textures[textureIndex], (int) model.deltaX * i, (int) model.deltaY * j, null);
                    }
                }
            } else {
                g.drawImage(textures[textureIndex], 0, 0, null);
            }
        }
    }

    @Override
    protected float getSpriteWidth() {
        TextureObjectModel textureModel = (TextureObjectModel) model;
        return textures[textureModel.getTextureIndex()].getWidth(null);
    }

    @Override
    protected float getSpriteHeight() {
        TextureObjectModel textureModel = (TextureObjectModel) model;
        return textures[textureModel.getTextureIndex()].getHeight(null);
    }

}
