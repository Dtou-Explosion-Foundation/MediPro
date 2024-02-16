package medipro.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
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
        ClassLoader classLoader = ImageUtil.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        if (inputStream == null) {
            logger.warning("Failed to load texture: " + path);
            return loadImageFromFile(new File(path));
        }
        try {
            return Optional.ofNullable(ImageIO.read(inputStream));
        } catch (IOException e) {
            logger.warning("Failed to load texture(2)" + path);
            return loadImageFromFile(new File(path));
        }
    }

    /**
     * Jarファイル外の画像を読み込む.
     * 
     * @param path 画像のパス
     * @return 画像
     */
    private static Optional<BufferedImage> loadImageFromFile(File file) {
        try {
            return Optional.ofNullable(ImageIO.read(file));
        } catch (IOException e) {
            logger.warning("Failed to load texture(3)" + file);
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

    /**
     * BufferedImageをByteBufferに変換する.
     * 
     * @param bufferedImage 変換するBufferedImage
     * @return 変換されたByteBuffer
     */
    public static ByteBuffer convertBufferedImageToByteBuffer(BufferedImage bufferedImage) {
        int[] pixels = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
        bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), pixels, 0,
                bufferedImage.getWidth());
        ByteBuffer buffer = ByteBuffer.allocateDirect(bufferedImage.getWidth() * bufferedImage.getHeight() * 4);

        for (int h = bufferedImage.getHeight() - 1; h >= 0; h--) {
            for (int w = 0; w < bufferedImage.getWidth(); w++) {
                int pixel = pixels[h * bufferedImage.getWidth() + w];

                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();
        return buffer;
    }
}
