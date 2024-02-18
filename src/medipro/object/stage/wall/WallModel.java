package medipro.object.stage.wall;

import java.awt.image.BufferedImage;
import java.util.Optional;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.util.ImageUtil;
import medipro.world.World;

/**
 * 壁のモデル.
 */
public class WallModel extends GameObjectModel {
    /**
     * 壁のモデルを生成する.
     * 
     * @param world オブジェクトが存在するワールド.
     */
    public WallModel(World world) {
        super(world);
        imageBlack = ImageUtil.loadImage("img/layers/medipro_0002s_0000_Wall_Black.png");
        imageWhite = ImageUtil.loadImage("img/layers/medipro_0002s_0001_Wall_White.png");
    }

    /**
     * 黒い壁のテクスチャ.
     */
    Optional<BufferedImage> imageBlack = Optional.empty();
    /**
     * 白い壁のテクスチャ.
     */
    Optional<BufferedImage> imageWhite = Optional.empty();
}
