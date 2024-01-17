package medipro.object.overlay.vignette;

import java.awt.image.BufferedImage;
import java.util.Optional;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.util.ImageUtil;

public class VignetteModel extends GameObjectModel {

    Optional<BufferedImage> image = Optional.empty();

    public VignetteModel(World world) {
        super(world);
        image = ImageUtil.loadImages("img/layers/medipro_0000_Vignette.png");
    }

}
