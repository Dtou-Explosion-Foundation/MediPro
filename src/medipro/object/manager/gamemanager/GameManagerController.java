package medipro.object.manager.gamemanager;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import medipro.config.InGameConfig;
import medipro.gui.panel.GamePanel;
import medipro.object.AnomalyListener;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.ResultWorld;

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
     * 乱数生成器.
     */
    private static Random random = new Random();

    /**
     * 異変を発生させる.
     */
    private void occurAnormaly() {
        logger.info("occurAnormaly");
        GameManagerModel gameManagerModel = (GameManagerModel) model;
        if (gameManagerModel.getCurrentAnomalyListener() != null) {
            gameManagerModel.getCurrentAnomalyListener().onAnomalyFinished();
            gameManagerModel.setCurrentAnomalyListener(null);
        }
        if (GameManagerModel.getFloor() == 0)
            return;

        if (random.nextDouble() > InGameConfig.CHANCE_OF_ANOMALY)
            return;

        List<AnomalyListener> listeners = Arrays.asList(this.model.getWorld().getAnormalyListeners().stream()
                .filter(listener -> listener.canAnomalyOccurred()).toArray(AnomalyListener[]::new));
        int occuredChanceSum = listeners.stream().map(listener -> listener.getOccurredChance()).reduce(0,
                (a, b) -> a + b);

        if (occuredChanceSum <= 0) {
            logger.warning("Not found enabled anomaly listener.");
            return;
        }

        final int[] occuredListenerIndexArray = new int[] { random.nextInt(occuredChanceSum) };
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
            int level = random
                    .nextInt(currentAnomalyListener.maxAnomalyLevel() - currentAnomalyListener.minAnomalyLevel() + 1)
                    + currentAnomalyListener.minAnomalyLevel();
            currentAnomalyListener.onAnomalyOccurred(level);
        }
    }

    @Override
    public void update(double dt) {
        // occurAnormaly();
    }

    /**
     * 初期の階に移動する.
     */
    public static void resetFloor() {
        GameManagerModel.resetFloor();
    }

    /**
     * 次の階に移動する.
     * 
     * @param isRight 右側から上るかどうか
     */
    public void nextFloor(boolean isRight) {
        GameManagerModel gameManagerModel = (GameManagerModel) model;
        if (gameManagerModel.isAnomalyListenerOccured()) {
            GamePanel gamePanel = this.model.getWorld().getPanel();
            gamePanel.setWorld(new ResultWorld(gamePanel));
        } else {
            gameManagerModel.nextFloor(isRight);
        }
        // ワールドを再生成するため必要ない
        // occurAnormaly();
    }

    /**
     * 前の階に移動する.
     * 
     * @param isRight 右側から上るかどうか
     */
    public void prevFloor(boolean isRight) {
        GameManagerModel gameManagerModel = (GameManagerModel) model;
        if (!gameManagerModel.isAnomalyListenerOccured()) {
            GamePanel gamePanel = this.model.getWorld().getPanel();
            gamePanel.setWorld(new ResultWorld(gamePanel));
        } else {
            gameManagerModel.prevFloor(isRight);
        }
        // ワールドを再生成するため必要ない
        // occurAnormaly();
    }

    /**
     * 前の階に移動できるかどうか.
     * 
     * @return 移動できるかどうか
     */
    public static boolean canGoPrevFloor() {
        return GameManagerModel.canGoPrevFloor();
    }
}
