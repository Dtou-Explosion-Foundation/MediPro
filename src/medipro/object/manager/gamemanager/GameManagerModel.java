package medipro.object.manager.gamemanager;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

public class GameManagerModel extends GameObjectModel {

    // private int stage = 0;

    public GameManagerModel(World world) {
        super(world);
        logger.info("gamemanagercontoller is " + world.getInstances(GameManagerController.class));
    }

}
