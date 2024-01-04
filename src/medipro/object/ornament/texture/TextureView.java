package medipro.object.ornament.texture;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

public class TextureView extends GameObjectView {
    private Image[] textures = null;

    public TextureView(GameObjectModel model) {
        super(model);
        TextureModel textureModel = (TextureModel) model;
        String[] texturePaths = textureModel.getTexturePaths();
        if (texturePaths != null) {
            textures = new Image[texturePaths.length];
            for (int i = 0; i < texturePaths.length; i++) {
                try {
                    textures[i] = ImageIO.read(new File(texturePaths[i]));
                } catch (IOException | NullPointerException e) {
                    logger.warning("Failed to load texture: " + texturePaths[i]);
                }
            }
        }
    }

    @Override
    protected void draw(Graphics2D g) {
        TextureModel textureModel = (TextureModel) model;
        int textureIndex = textureModel.getTextureIndex();
        if (textures != null && textureIndex >= 0 && textureIndex < textures.length) {
            g.drawImage(textures[textureIndex], 0, 0, null);
        }
    }
}
