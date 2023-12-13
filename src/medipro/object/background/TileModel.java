package medipro.object.background;

import java.awt.Image;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

public class TileModel extends GameObjectModel {

    public Image image;
    public double originX = 0;
    public double originY = 0;
    public int width;
    public int height;

    public TileModel(World world, Image image) {
        super(world);
        this.image = image;
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);

    }
}
