package medipro.object.stage.ceil;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Optional;

import medipro.object.base.World;
import medipro.object.base.gridobject.GridObjectModel;
import medipro.util.ImageUtil;

/**
 * 天井のモデル.
 */
public class CeilModel extends GridObjectModel {
    /**
     * テクスチャ.
     */
    private Optional<Image> texture = Optional.empty();

    /**
     * テクスチャを取得する.
     * 
     * @return テクスチャ
     */
    public Optional<Image> getTexutre() {
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
     * 天井のモデルを生成する.
     * 
     * @param world ワールド
     * @param path  テクスチャのパス
     */
    public CeilModel(World world, String path) {
        this(world, ImageUtil.loadImage(path));
    }

    /**
     * 天井のモデルを生成する.
     * 
     * @param world   ワールド
     * @param texture テクスチャ
     */
    public CeilModel(World world, Optional<BufferedImage> texture) {
        super(world, texture.isPresent() ? texture.get().getWidth(null) : 0,
                texture.isPresent() ? texture.get().getHeight(null) : 0);
        if (texture.isPresent())
            setTexture(texture.get());
    }

    /**
     * 天井のモデルを生成する.
     * 
     * @param world ワールド
     */
    public CeilModel(World world) {
        this(world, "img/layers/medipro_0003_Ceil.png");
    }

    /**
     * 天井のモデルを生成する.
     * 
     * @param world  ワールド
     * @param width  幅
     * @param height 高さ
     */
    public CeilModel(World world, int width, int height) {
        super(world, width, height);
    }

}
