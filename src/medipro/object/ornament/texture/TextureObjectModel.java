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

    private int occurredChance = 1;

    public int getOccurredChance() {
        return occurredChance;
    }

    public void setOccurredChance(int occurredChance) {
        this.occurredChance = occurredChance;
    }

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

    /**
     * 現在のテクスチャの時間間隔を取得する.
     * 
     * @return テクスチャの時間間隔
     */
    public double getInterval() {
        return interval;
    }

    /**
     * 現在のテクスチャの時間間隔を設定する.
     * 
     * @param interval テクスチャの時間間隔
     */
    public void setInterval(double interval) {
        this.interval = interval;
    }

    /**
     * 現在のテクスチャのx変数を取得する
     * 
     * @return テクスチャのx変数
     */
    public double getDeltaX() {
        return deltaX;
    }

    /**
     * 現在のテクスチャのx変数を設定する
     * 
     * @param deltaX テクスチャのx変数
     */
    public void setDeltaX(double deltaX) {
        this.deltaX = deltaX;
    }

    /**
     * 現在のテクスチャのy変数を取得する
     * 
     * @return テクスチャのy変数
     */
    public double getDeltaY() {
        return deltaY;
    }

    /**
     * 現在のテクスチャのy変数を設定する
     * 
     * @param deltaY テクスチャのy変数
     */
    public void setDeltaY(double deltaY) {
        this.deltaY = deltaY;
    }
    
    /**
     * ダミーの有無
     */
    public boolean isDummies = false;

    /**
     * 
     * @return ダミーの有無
     */
    public boolean hasDummies() {
        return isDummies;
    }

}
