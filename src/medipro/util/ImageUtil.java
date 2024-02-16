package medipro.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * 画像を扱うユーティリティクラス.
 */
public class ImageUtil {
    /**
     * ロガー.
     */
    protected final static Logger logger = Logger.getLogger(ImageUtil.class.getName());

    /**
     * 画像を読み込む.
     * 
     * @param path 画像のパス
     * @return 画像
     */
    public static Optional<BufferedImage> loadImage(String path) {
        // try {
        //     return Optional.ofNullable(ImageIO.read(new File(path)));
        // } catch (IOException e) {
        //     logger.warning("Failed to load image");
        //     e.printStackTrace();
        //     return Optional.empty();
        // }

        ClassLoader classLoader = ImageUtil.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        if (inputStream == null) {
            logger.warning("Failed to load texture: " + path);
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(ImageIO.read(inputStream));
        } catch (IOException e) {
            logger.warning("Failed to load texture(2)" + path);
            return Optional.empty();
        }
    }

    /**
     * 画像を読み込む.
     * 
     * @param path 画像のパス
     * @return 画像
     */
    public static BufferedImage invertX(BufferedImage image) {
        if (image == null)
            return null;
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(image, null);
    }
}
