package medipro.object.overlay.vignette;

import java.awt.image.BufferedImage;
import java.util.Optional;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.util.ImageUtil;

/**
 * ビネットのモデル.
 */
public class VignetteModel extends GameObjectModel {

    /**
     * テクスチャ.
     */
    private Optional<BufferedImage> texture = Optional.empty();

    /**
     * テクスチャを取得する.
     * 
     * @return テクスチャ.
     */
    public Optional<BufferedImage> getTexture() {
        return texture;
    }

    /**
     * テクスチャを設定する.
     * 
     * @param texture テクスチャ.
     */
    public void setTexture(Optional<BufferedImage> texture) {
        this.texture = texture;
    }

    /**
     * ビネットのモデルを生成する.
     * 
     * @param world オブジェクトが存在するワールド.
     */
    public VignetteModel(World world) {
        super(world);
        setTexture(ImageUtil.loadImages("img/layers/medipro_0000_Vignette.png"));
    }

}
