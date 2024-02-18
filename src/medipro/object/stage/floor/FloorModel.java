package medipro.object.stage.floor;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Optional;

import medipro.object.base.gridobject.GridObjectModel;
import medipro.util.ImageUtil;
import medipro.world.World;

/**
 * 床のモデル.
 */
public class FloorModel extends GridObjectModel {
    /**
     * テクスチャ.
     */
    private Optional<Image> texture = Optional.empty();

    /**
     * テクスチャを取得する.
     * 
     * @return テクスチャ
     */
    public Optional<Image> getTexture() {
        return texture;
    }

    /**
     * テクスチャを設定する.
     * 
     * @param texture テクスチャ
     */
    public void setTexture(Optional<Image> texture) {
        this.texture = texture;
    }

    /**
     * テクスチャを設定する.
     * 
     * @param texture テクスチャ
     */
    public void setTexture(Image texture) {
        this.texture = Optional.ofNullable(texture);
    }

    /**
     * 床のモデルを生成する.
     * 
     * @param world ワールド
     * @param path  テクスチャのパス
     */
    public FloorModel(World world, String path) {
        this(world, ImageUtil.loadImage(path));
    }

    /**
     * 床のモデルを生成する.
     * 
     * @param world   ワールド
     * @param texture テクスチャ
     */
    public FloorModel(World world, Optional<BufferedImage> texture) {
        // super(world, texture.getWidth(null), texture.getHeight(null));
        super(world, texture.isPresent() ? texture.get().getWidth(null) : 0,
                texture.isPresent() ? texture.get().getHeight(null) : 0);
        if (texture.isPresent())
            setTexture(texture.get());
    }

    /**
     * 床のモデルを生成する.
     * 
     * @param world ワールド
     */
    public FloorModel(World world) {
        this(world, "img/layers/medipro_0004_Floor.png");
    }

    /**
     * 床のモデルを生成する.
     * 
     * @param world  ワールド
     * @param width  幅
     * @param height 高さ
     */
    public FloorModel(World world, int width, int height) {
        super(world, width, height);
    }

}
