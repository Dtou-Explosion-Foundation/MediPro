package medipro.object.background;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

public class BackgroundModel extends GameObjectModel {
    // private GameObjectModel model;
    // private double x = model.x;
    public BackgroundModel(World world) {
        super(world);
        logger.info("BackgroundModel created");
    }
}