package medipro.object.background;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.World;
public class BackgroundModel extends GameObjectModel {
    //private GameObjectModel model;
    //private double x = model.x;
    public BackgroundModel(World world){
        super(world);
        logger.info("BackgroundModel created");
    }
}
