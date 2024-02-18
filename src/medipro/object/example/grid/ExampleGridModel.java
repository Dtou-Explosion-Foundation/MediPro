package medipro.object.example.grid;

import java.awt.Image;
import java.util.Optional;

import medipro.object.base.gridobject.GridObjectModel;
import medipro.world.World;

/**
 * グリッドオブジェクトの例のモデル.
 */
public class ExampleGridModel extends GridObjectModel {

    /**
     * グリッド内部に表示するテクスチャ.
     */
    private Optional<Image> texture = Optional.empty();

    /**
     * グリッド内部に表示するテクスチャを取得する.
     * 
     * @return グリッド内部に表示するテクスチャ.
     */
    public Optional<Image> getTexture() {
        return texture;
    }

    /**
     * グリッド内部に表示するテクスチャを設定する.
     * 
     * @param texture グリッド内部に表示するテクスチャ.
     */
    public void setTexture(Optional<Image> texture) {
        this.texture = texture;
    }

    /**
     * グリッド内部に表示するテクスチャを設定する.
     * 
     * @param texture グリッド内部に表示するテクスチャ.
     */
    public void setTexture(Image texture) {
        this.texture = Optional.ofNullable(texture);
    }

    /**
     * グリッドオブジェクトのモデルを生成する.
     * 
     * @param world  オブジェクトが存在するワールド.
     * @param width  グリッドの幅.
     * @param height グリッドの高さ.
     */
    public ExampleGridModel(World world, int width, int height) {
        super(world, width, height);
    }

    /**
     * グリッドオブジェクトのモデルを生成する.
     * 
     * @param world   オブジェクトが存在するワールド.
     * @param texture グリッド内部に表示する画像.
     */
    public ExampleGridModel(World world, Image texture) {
        super(world, texture.getWidth(null), texture.getHeight(null));
        setTexture(texture);
    }

}
