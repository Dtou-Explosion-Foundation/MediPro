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
    public int width;
    /**
     * グリッドの高さ.
     */
    public int height;

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
