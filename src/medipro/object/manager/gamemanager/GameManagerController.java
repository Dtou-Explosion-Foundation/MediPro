package medipro.object.manager.gamemanager;

import java.util.List;

import medipro.object.AnomalyListener;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

public class GameManagerController extends GameObjectController {
    /**
     * ゲームオブジェクトコントローラを生成する.
     * 
     * @param models 格納するモデル
     */
    public GameManagerController(GameObjectModel model) {
        super(model);
    }

    AnomalyListener currentAnomalyListener;

    private void occurAnormaly() {
        if (currentAnomalyListener != null)
            return;
        logger.info("GameManager::occurAnormaly");

        List<AnomalyListener> listeners = this.model.world.getAnormalyListeners().stream()
                .filter(listener -> listener.canAnomalyOccurred()).toList();
        int occuredChanceSum = listeners.stream().map(listener -> listener.getOccurredChance()).reduce(0,
                (a, b) -> a + b);

        // int occuredListenerIndex = (int) (Math.random() * occuredChanceSum);

        // currentAnomalyListener = listeners.reduce((a, b) -> {
        //     if (occuredListenerIndex < a.getOccuredChance()) {
        //         return a;
        //     } else {
        //         occuredListenerIndex -= a.getOccuredChance();
        //         return b;
        //     }
        // }).get();

        final int[] occuredListenerIndexArray = new int[] { (int) (Math.random() * occuredChanceSum) };

        currentAnomalyListener = listeners.stream().reduce((a, b) -> {
            if (occuredListenerIndexArray[0] < a.getOccurredChance()) {
                return a;
            } else {
                occuredListenerIndexArray[0] -= a.getOccurredChance();
                return b;
            }
        }).get();

        int level = (int) (Math.random()
                * (currentAnomalyListener.maxAnomalyLevel() - currentAnomalyListener.minAnomalyLevel() + 1))
                + currentAnomalyListener.minAnomalyLevel();
        currentAnomalyListener.onAnomalyOccurred(level);
        // for (int i = 0; i < listeners.size(); i++) {
        //     occuredListenerIndex -= listeners.get(i).getOccuredChance();
        //     if (occuredListenerIndex < 0) {
        //         currentAnomalyListener = listeners.get(i);
        //         break;
        //     }
        // }
    }

    @Override
    public void update(double dt) {
        occurAnormaly();
    }

}
