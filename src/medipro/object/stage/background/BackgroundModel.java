package medipro.object.stage.background;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Optional;

import medipro.object.base.World;
import medipro.object.base.gridobject.GridObjectModel;
import medipro.util.ImageUtil;

/**
 * 背景のモデル.
 */
public class BackgroundModel extends GridObjectModel {

    /**
     * 画像.
     */
    public Optional<Image> image = Optional.empty();

    /**
     * 背景のモデルを生成する.
     * 
     * @param world ワールド
     * @param image 画像
     */
    public BackgroundModel(World world, Optional<BufferedImage> image) {
        super(world, image.isPresent() ? image.get().getWidth(null) : 0,
                image.isPresent() ? image.get().getHeight(null) : 0);
        this.image = Optional.ofNullable(image.orElse(null));
    }

    /**
     * 背景のモデルを生成する.
     * 
     * @param world ワールド
     */
    public BackgroundModel(World world) {
        this(world, ImageUtil.loadImage("img/layers/medipro_0004s_0000_Background_Grid.png"));
    }

    /**
     * 背景のモデルを生成する.
     * 
     * @param world  ワールド
     * @param width  画像の横幅
     * @param height 画像の縦幅
     */
    public BackgroundModel(World world, int width, int height) {
        super(world, width, height);
    }
}