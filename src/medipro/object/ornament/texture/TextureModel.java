package medipro.object.ornament.texture;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * テクスチャのモデル.
 */
public class TextureModel extends GameObjectModel {

    /**
     * テクスチャのパス.
     */
    private String[] texturePaths;
    /**
     * 現在のテクスチャのインデックス.
     */
    private int textureIndex = 0;

    /**
     * テクスチャのモデルを生成する.
     * 
     * @param world        オブジェクトが存在するワールド.
     * @param texturePaths テクスチャのパス
     * @param textureIndex 現在のテクスチャのインデックス
     */
    public TextureModel(World world, String[] texturePaths, int textureIndex) {
        super(world);
        this.texturePaths = texturePaths;
        this.textureIndex = textureIndex;
    }

    /**
     * テクスチャのモデルを生成する.
     * 
     * @param world        オブジェクトが存在するワールド.
     * @param texturePaths テクスチャのパス
     */
    public TextureModel(World world, String[] texturePaths) {
        this(world, texturePaths, 0);
    }

    /**
     * テクスチャのモデルを生成する.
     * 
     * @param world オブジェクトが存在するワールド.
     */
    public TextureModel(World world) {
        this(world, null, 0);
    }

    /**
     * テクスチャのパスを取得する.
     * 
     * @return テクスチャのパス
     */
    public String[] getTexturePaths() {
        return texturePaths;
    }

    /**
     * テクスチャのパスを設定する.
     * 
     * @param texturePaths テクスチャのパス
     */
    public void setTexturePaths(String[] texturePaths) {
        this.texturePaths = texturePaths;
    }

    /**
     * 現在のテクスチャのインデックスを取得する.
     * 
     * @return テクスチャのインデックス
     */
    public int getTextureIndex() {
        return textureIndex;
    }

    /**
     * 現在のテクスチャのインデックスを設定する.
     * 
     * @param textureIndex テクスチャのインデックス
     */
    protected void setTextureIndex(int textureIndex) {
        this.textureIndex = Math.max(0, Math.min(texturePaths.length - 1, textureIndex));
    }

}
