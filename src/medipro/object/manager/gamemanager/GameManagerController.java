package medipro.object.manager.gamemanager;

import java.util.Arrays;
import java.util.List;

import medipro.object.AnomalyListener;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * ゲームマネージャのコントローラ.
 */
public class GameManagerController extends GameObjectController {
    /**
     * ゲームマネージャのコントローラを生成する.
     * 
     * @param model 対象のモデル
     */
    public GameManagerController(GameObjectModel model) {
        super(model);
    }

    @Override
    public void postSetupWorld() {
        occurAnormaly();
    }

    /**
     * 異変を発生させる.
     */
    private void occurAnormaly() {
        GameManagerModel gameManagerModel = (GameManagerModel) model;
        if (gameManagerModel.getCurrentAnomalyListener() != null) {
            gameManagerModel.getCurrentAnomalyListener().onAnomalyFinished();
            gameManagerModel.setCurrentAnomalyListener(null);
        }
        List<AnomalyListener> listeners = Arrays.asList(this.model.world.getAnormalyListeners().stream()
                .filter(listener -> listener.canAnomalyOccurred()).toArray(AnomalyListener[]::new));
        int occuredChanceSum = listeners.stream().map(listener -> listener.getOccurredChance()).reduce(0,
                (a, b) -> a + b);

        final int[] occuredListenerIndexArray = new int[] { (int) (Math.random() * occuredChanceSum) };

        AnomalyListener currentAnomalyListener = listeners.stream().reduce((a, b) -> {
            if (occuredListenerIndexArray[0] < a.getOccurredChance()) {
                return a;
            } else {
                occuredListenerIndexArray[0] -= a.getOccurredChance();
                return b;
            }
        }).orElseGet(() -> null);

        if (currentAnomalyListener != null) {
            gameManagerModel.setCurrentAnomalyListener(currentAnomalyListener);
            int level = (int) (Math.random()
                    * (currentAnomalyListener.maxAnomalyLevel() - currentAnomalyListener.minAnomalyLevel() + 1))
                    + currentAnomalyListener.minAnomalyLevel();
            currentAnomalyListener.onAnomalyOccurred(level);
        }
    }

    @Override
    public void update(double dt) {
        // occurAnormaly();
    }

    /**
     * 次の階に移動する.
     */
    public void nextFloor(boolean isRight) {
        GameManagerModel gameManagerModel = (GameManagerModel) model;
        gameManagerModel.nextFloor(isRight);
        occurAnormaly();

    }

    /**
     * 前の階に移動する.
     */
    public void prevFloor(boolean isRight) {
        GameManagerModel gameManagerModel = (GameManagerModel) model;
        gameManagerModel.prevFloor(isRight);
        occurAnormaly();
    }
}
