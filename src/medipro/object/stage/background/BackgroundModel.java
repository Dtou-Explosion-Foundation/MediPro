package medipro.object.stage.background;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import medipro.object.base.World;
import medipro.object.base.gridobject.GridObjectModel;

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
     * @param path  画像のパス
     * @throws IOException 画像の読み込みに失敗した場合
     */
    public BackgroundModel(World world, String path) throws IOException {
        this(world, ImageIO.read(new File(path)));
    }

    /**
     * 背景のモデルを生成する.
     * 
     * @param world ワールド
     * @param image 画像
     */
    public BackgroundModel(World world, Image image) {
        super(world, image.getWidth(null), image.getHeight(null));
        this.image = Optional.ofNullable(image);
    }

    /**
     * 背景のモデルを生成する.
     * 
     * @param world ワールド
     * @throws IOException 画像の読み込みに失敗した場合
     */
    public BackgroundModel(World world) throws IOException {
        this(world, "img/layers/medipro_0004s_0000_Background_Grid.png");
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