package medipro.object.background;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

public class BackgroundModel extends GameObjectModel {

    public BackgroundModel(World world){
        super(world);
        logger.info("BackgroundModel created");
    }
}
