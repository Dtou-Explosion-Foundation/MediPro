package medipro.object.ornament.marker;

import java.awt.Color;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

public class MarkerModel extends GameObjectModel {
    public MarkerModel(World world) {
        super(world);
    }

    public int radius = 5;
    public Color color = Color.RED;
}
