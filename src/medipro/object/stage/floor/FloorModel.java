package medipro.object.stage.floor;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import medipro.object.base.World;
import medipro.object.base.gridobject.GridObjectModel;

public class FloorModel extends GridObjectModel {
    Optional<Image> image = Optional.empty();

    public FloorModel(World world, String path) throws IOException {
        this(world, ImageIO.read(new File(path)));
    }

    public FloorModel(World world, Image image) {
        super(world, image.getWidth(null), image.getHeight(null));
        this.image = Optional.ofNullable(image);
    }

    public FloorModel(World world) throws IOException {
        this(world, "img/layers/medipro_0004_Floor.png");
    }

    public FloorModel(World world, int width, int height) {
        super(world, width, height);
    }

}
