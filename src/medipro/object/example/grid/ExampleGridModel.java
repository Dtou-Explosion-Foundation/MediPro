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
     * グリッド内部に表示する画像.
     */
    Optional<Image> image = Optional.empty();

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
     * @param world オブジェクトが存在するワールド.
     * @param image グリッド内部に表示する画像.
     */
    public ExampleGridModel(World world, Image image) {
        super(world, image.getWidth(null), image.getHeight(null));
        this.image = Optional.ofNullable(image);
    }

}
