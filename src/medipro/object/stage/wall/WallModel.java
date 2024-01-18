package medipro.object.stage.wall;

import java.awt.image.BufferedImage;
import java.util.Optional;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.util.ImageUtil;

public class WallModel extends GameObjectModel {
    public WallModel(World world) {
        super(world);
        image_black = ImageUtil.loadImages("img/layers/medipro_0002s_0000_Wall_Black.png");
        image_white = ImageUtil.loadImages("img/layers/medipro_0002s_0001_Wall_White.png");
    }

    Optional<BufferedImage> image_black = Optional.empty();
    Optional<BufferedImage> image_white = Optional.empty();
}
