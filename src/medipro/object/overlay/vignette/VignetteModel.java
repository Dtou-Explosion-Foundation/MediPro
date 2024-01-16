package medipro.object.overlay.vignette;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

public class VignetteModel extends GameObjectModel {

    Optional<BufferedImage> image = Optional.empty();

    public VignetteModel(World world) {
        super(world);
        try {
            image = Optional.ofNullable(ImageIO.read(new File("img/layers/medipro_0000_ビネット.png")));
        } catch (IOException e) {
            logger.warning("Failed to load image");
            e.printStackTrace();
        }
    }

}
