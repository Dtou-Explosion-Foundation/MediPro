package medipro.object.stage.ceil;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import medipro.object.base.World;
import medipro.object.base.gridobject.GridObjectModel;

public class CeilModel extends GridObjectModel {
    Optional<Image> image = Optional.empty();

    public CeilModel(World world, String path) throws IOException {
        this(world, ImageIO.read(new File(path)));
    }

    public CeilModel(World world, Image image) {
        super(world, image.getWidth(null), image.getHeight(null));
        this.image = Optional.ofNullable(image);
    }

    public CeilModel(World world) throws IOException {
        this(world, "img/layers/medipro_0003_Ceil.png");
    }

    public CeilModel(World world, int width, int height) {
        super(world, width, height);
    }

}
