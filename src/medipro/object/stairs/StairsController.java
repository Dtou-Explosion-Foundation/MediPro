package medipro.object.stairs;

import java.util.List;

import javax.swing.JPanel;

import medipro.gui.panel.IGamePanel;
import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.manager.gamemanager.GameManagerController;
import medipro.object.overlay.blackfilter.BlackFilterController;
import medipro.object.player.AutoWalker;
import medipro.object.player.PlayerController;
import medipro.object.player.PlayerModel;

/**
 * 階段のコントローラ.
 */
public class StairsController extends GameObjectController {
    /**
     * プレイヤーのコントローラー
     */
    private PlayerController playerController;
    /**
     * ブラックフィルターのコントローラー
     */
    private BlackFilterController blackFilterController;
    /**
     * ゲームマネージャーのコントローラー
     */
    private GameManagerController gameManagerController;

    /**
     * 生成直後にプレイヤーを階段の上に移動させるためのオートウォーカー
     */
    private static AutoWalker startAutoWalker;

    /**
     * 階段のコントローラーを生成する.
     * 
     * @param model 対象のモデル
     */
    public StairsController(GameObjectModel model) {
        super(model);
    }

    @Override
    public void postSetupWorld() {
        List<PlayerController> playerControllers = this.model.world.getControllers(PlayerController.class);
        if (playerControllers.size() > 0) {
            this.playerController = playerControllers.get(0);
        }

        List<BlackFilterController> blackFilterControllers = this.model.world
                .getControllers(BlackFilterController.class);
        if (blackFilterControllers.size() > 0) {
            this.blackFilterController = blackFilterControllers.get(0);
        }

        List<GameManagerController> gameManagerControllers = this.model.world
                .getControllers(GameManagerController.class);
        if (gameManagerControllers.size() > 0) {
            this.gameManagerController = gameManagerControllers.get(0);
        }

        if (startAutoWalker != null && playerController != null) {
            playerController.pushAutoWalker(startAutoWalker);
            startAutoWalker = null;
            isPlayerOnStairs = true;

            if (blackFilterController != null && startBlackFilterDuration > 0)
                blackFilterController.blackOut(startBlackFilterDuration);
        }

    }

    /**
     * プレイヤーが階段の上にいるかどうか.階段に入った判定が連続で行われないようにするためのフラグ.
     */
    private boolean isPlayerOnStairs = true;
    /**
     * 階段に入ったときに表示するブラックフィルターの明転時間.
     */
    private static float startBlackFilterDuration = 0;

    @Override
    public void update(double dt) {
        if (playerController == null)
            return;

        StairsModel stairsModel = (StairsModel) model;
        PlayerModel playerModel = (PlayerModel) playerController.model;
        float triggerRange = stairsModel.getTriggerRange() / 2;
        if (playerModel.x > model.x - triggerRange && playerModel.x < model.x + triggerRange) {
            if (isPlayerOnStairs || playerModel.isPlayerAutoWalking())
                return;
            isPlayerOnStairs = true;

            AutoWalker endAutoWalker = new AutoWalker(playerModel.x, playerModel.y,
                    model.x + stairsModel.getWidth() / 2 * (stairsModel.isLeftUp() ? -1 : 1),
                    model.y + stairsModel.getHeight() / 2);
            double endDuration = endAutoWalker.setSpeed(playerModel.speedLimitX);
            if (blackFilterController != null)
                blackFilterController.blackIn((float) endDuration);
            final double playerY = playerModel.y;
            endAutoWalker.setCallback(() -> {
                startAutoWalker = new AutoWalker(
                        model.x + stairsModel.getWidth() / 2 * (stairsModel.isLeftUp() ? -1 : 1),
                        model.y - stairsModel.getHeight() / 2,
                        model.x + stairsModel.getTriggerRange() / 2 * 1.2 * (stairsModel.isLeftUp() ? 1 : -1), playerY);

                startBlackFilterDuration = (float) startAutoWalker.setSpeed(playerModel.speedLimitX);

                if (gameManagerController != null)
                    gameManagerController.nextFloor();

                World newWorld;
                try {
                    newWorld = this.model.world.getClass().getDeclaredConstructor(JPanel.class)
                            .newInstance(this.model.world.getPanel());
                } catch (Exception e) {
                    logger.severe(e.getMessage());
                    e.printStackTrace();
                    return;
                }
                IGamePanel panel = (IGamePanel) this.model.world.getPanel();
                panel.setWorld(newWorld);
            });
            playerController.pushAutoWalker(endAutoWalker);
        } else

        {
            isPlayerOnStairs = false;
        }
    }
}
