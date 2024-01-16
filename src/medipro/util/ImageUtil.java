package medipro.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class ImageUtil {
    protected final static Logger logger = Logger.getLogger(ImageUtil.class.getName());

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
