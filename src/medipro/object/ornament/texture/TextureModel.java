package medipro.object.ornament.texture;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

public class TextureModel extends GameObjectModel {

    private String[] texturePaths;
    private int textureIndex = 0;

    public TextureModel(World world, String[] texturePaths, int textureIndex) {
        super(world);
        this.texturePaths = texturePaths;
        this.textureIndex = textureIndex;
    }

    public TextureModel(World world, String[] texturePaths) {
        this(world, texturePaths, 0);
    }

    public TextureModel(World world) {
        this(world, null, 0);
    }

    public String[] getTexturePaths() {
        return texturePaths;
    }

    public void setTexturePaths(String[] texturePaths) {
        this.texturePaths = texturePaths;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    protected void setTextureIndex(int textureIndex) {
        this.textureIndex = Math.max(0, Math.min(texturePaths.length - 1, textureIndex));
    }

}
