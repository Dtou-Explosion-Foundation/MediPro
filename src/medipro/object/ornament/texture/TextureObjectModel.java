package medipro.object.ornament.texture;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * テクスチャのモデル.
 */
public class TextureObjectModel extends GameObjectModel {

    /**
     * テクスチャのパス.
     */
    private String[] texturePaths;
    /**
     * 現在のテクスチャのインデックス.
     */
    private int textureIndex = 0;

    public double interval;

    public double deltaX;
    public double deltaY;
    public double delta2X;
    public double delta2Y;

    public int timesX;
    public int timesY;

    /**
     * テクスチャのモデルを生成する.
     * 
     * @param world        オブジェクトが存在するワールド.
     * @param texturePaths テクスチャのパス
     * @param textureIndex 現在のテクスチャのインデックス
     */
    public TextureObjectModel(World world, String[] texturePaths, int textureIndex) {
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
    public TextureObjectModel(World world, String[] texturePaths) {
        this(world, texturePaths, 0);
    }

    /**
     * テクスチャのモデルを生成する.
     * 
     * @param world オブジェクトが存在するワールド.
     */
    public TextureObjectModel(World world) {
        this(world, null, 0);
        this.interval = 1;

        this.deltaX = 0;
        this.deltaY = 0;
        this.delta2X = 0;
        this.delta2Y = 0;

        this.timesX = 0;
        this.timesY = 0;
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
    public void setTextureIndex(int textureIndex) {
        this.textureIndex = Math.max(0, Math.min(texturePaths.length - 1, textureIndex));
    }

    public double getInterval() {
        return interval;
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }

    public double getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(double deltaX) {
        this.deltaX = deltaX;
    }

    public double getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(double deltaY) {
        this.deltaY = deltaY;
    }

    public boolean isDummies = false;

    public boolean hasDummies() {
        return isDummies;
    }

}
