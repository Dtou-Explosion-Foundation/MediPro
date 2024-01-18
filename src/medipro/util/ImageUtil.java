package medipro.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    public static Optional<BufferedImage> loadImages(String path) {
        try {
            return Optional.ofNullable(ImageIO.read(new File(path)));
        } catch (IOException e) {
            logger.warning("Failed to load image");
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
