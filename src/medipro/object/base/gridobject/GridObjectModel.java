package medipro.object.base.gridobject;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

/**
 * グリッドオブジェクトのモデル.
 */
public abstract class GridObjectModel extends GameObjectModel {

    /**
     * グリッドの幅.
     */
    private int width;

    /**
     * グリッドの幅を取得する.
     * 
     * @return グリッドの幅.
     */
    public int getWidth() {
        return width;
    }

    /**
     * グリッドの幅を設定する.
     * 
     * @param width グリッドの幅.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * グリッドの高さ.
     */
    private int height;

    /**
     * グリッドの高さを取得する.
     * 
     * @return グリッドの高さ.
     */
    public int getHeight() {
        return height;
    }

    /**
     * グリッドの高さを設定する.
     * 
     * @param height グリッドの高さ.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * グリッドオブジェクトのモデルを生成する.
     * 
     * @param world  オブジェクトが存在するワールド.
     * @param width  グリッドの幅.
     * @param height グリッドの高さ.
     */
    public GridObjectModel(World world, int width, int height) {
        super(world);
        this.width = width;
        this.height = height;
    }
}
