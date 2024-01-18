package medipro.object.stage.ceil;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import medipro.object.base.World;
import medipro.object.base.gridobject.GridObjectModel;

/**
 * 天井のモデル.
 */
public class CeilModel extends GridObjectModel {
    /**
     * テクスチャ
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
     * @throws IOException テクスチャをロードできなかった時のエラー
     */
    public CeilModel(World world, String path) throws IOException {
        this(world, ImageIO.read(new File(path)));
    }

    /**
     * 天井のモデルを生成する.
     * 
     * @param world   ワールド
     * @param texture テクスチャ
     */
    public CeilModel(World world, Image texture) {
        super(world, texture.getWidth(null), texture.getHeight(null));
        setTexture(texture);
    }

    /**
     * 天井のモデルを生成する.
     * 
     * @param world ワールド
     * @throws IOException テクスチャをロードできなかった時のエラー
     */
    public CeilModel(World world) throws IOException {
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
