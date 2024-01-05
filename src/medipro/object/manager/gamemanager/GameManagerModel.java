package medipro.object.manager.gamemanager;

import medipro.object.AnomalyListener;
import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

public class GameManagerModel extends GameObjectModel {

    // private int stage = 0;
    private AnomalyListener currentAnomalyListener;

    private static int floor = 0;

    public static int getFloor() {
        return floor;
    }

    public static void setFloor(int floor) {
        GameManagerModel.floor = floor;
    }

    public static void incrementFloor() {
        GameManagerModel.floor++;
    }

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
        logger.info("GameManager Floor: " + floor);
    }
}
