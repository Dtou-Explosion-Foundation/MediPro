package medipro.object.manager.gamemanager;

import medipro.object.AnomalyListener;
import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

public class GameManagerModel extends GameObjectModel {

    // private int stage = 0;
    private AnomalyListener currentAnomalyListener;

    public AnomalyListener getCurrentAnomalyListener() {
        return currentAnomalyListener;
    }

    public void setCurrentAnomalyListener(AnomalyListener currentAnomalyListener) {
        this.currentAnomalyListener = currentAnomalyListener;
    }

    public GameManagerModel(World world) {
        super(world);
        // logger.info("GameManagerModel created");
        // logger.info("MarkerController is " +
        // world.getControllers(MarkerController.class));
    }
}
