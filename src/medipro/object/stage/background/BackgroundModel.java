package medipro.object.stage.background;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import medipro.object.base.World;
import medipro.object.base.gridobject.GridObjectModel;

public class BackgroundModel extends GridObjectModel {

    Optional<Image> image = Optional.empty();

    public BackgroundModel(World world, String path) throws IOException {
        this(world, ImageIO.read(new File(path)));
    }

    public BackgroundModel(World world, Image image) {
        super(world, image.getWidth(null), image.getHeight(null));
        this.image = Optional.ofNullable(image);
    }

    public BackgroundModel(World world) throws IOException {
        this(world, "img/コンクリ壁_grid.png");
    }

    public BackgroundModel(World world, int width, int height) {
        super(world, width, height);
    }
}