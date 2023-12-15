package medipro.object.example.grid;

import java.awt.Image;
import java.util.Optional;

import medipro.object.base.gridobject.GridObjectModel;
import medipro.world.World;

public class ExampleGridModel extends GridObjectModel {

    Optional<Image> image = Optional.empty();

    public ExampleGridModel(World world, int width, int height) {
        super(world, width, height);
    }

    public ExampleGridModel(World world, Image image) {
        super(world, image.getWidth(null), image.getHeight(null));
        this.image = Optional.ofNullable(image);
    }

}
