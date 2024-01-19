package medipro.object.stairs;

import java.util.List;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.manager.gamemanager.GameManagerController;
import medipro.object.manager.gamemanager.GameManagerModel;
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

        // それぞれのコントローラーをワールドから取得する.
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

        // 階段を登るアニメーションを発行する.

        StairsModel stairsModel = (StairsModel) model;
        PlayerModel playerModel = (PlayerModel) playerController.model;
        if (stairsModel.isRightUp() != GameManagerModel.getFloorChangingState().isRight())
            return;
        logger.fine("StartAutoWalker: isRightUp:" + stairsModel.isRightUp() + "  FloorChangingState:"
                + GameManagerModel.getFloorChangingState());
        startAutoWalker = new AutoWalker(model.x + stairsModel.getWidth() / 2 * (stairsModel.isRightUp() ? 1 : -1),
                model.y - stairsModel.getHeight() / 2
                        * (GameManagerModel.getFloorChangingState().isUpWhenOn(stairsModel.isRightUp()) ? 1 : -1),
                model.x + stairsModel.getTriggerRange() / 2 * 1.2 * (stairsModel.isRightUp() ? -1 : 1), playerModel.y);

        startBlackFilterDuration = (float) startAutoWalker.setSpeed(playerModel.speedLimitX);

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
     * 階段を出るときに表示するブラックフィルターの明転時間.
     */
    private static float startBlackFilterDuration = 0;

    @Override
    public void update(double dt) {
        if (playerController == null)
            return;

        StairsModel stairsModel = (StairsModel) model;
        PlayerModel playerModel = (PlayerModel) playerController.model;
        float triggerRange = stairsModel.getTriggerRange() / 2;
        // 階段の範囲外のためスキップ
        if (playerModel.x < model.x - triggerRange || playerModel.x > model.x + triggerRange) {
            isPlayerOnStairs = false;
            return;
        }
        // すでに階段に侵入した時の処理が行われているか、プレイヤーが自動歩行中のためスキップ
        if (isPlayerOnStairs || playerModel.isPlayerAutoWalking())
            return;
        isPlayerOnStairs = true;

        // 階段に入った時の処理
        // 現在の位置から、階段の端までのオートウォーカーを生成する.
        logger.fine("EndAutoWalker: isRightUp:" + stairsModel.isRightUp() + "  FloorChangingState:"
                + GameManagerModel.getFloorChangingState());
        AutoWalker endAutoWalker = new AutoWalker(playerModel.x, playerModel.y,
                model.x + stairsModel.getWidth() / 2 * (stairsModel.isRightUp() ? 1 : -1),
                model.y + stairsModel.getHeight() / 2
                        * (GameManagerModel.getFloorChangingState().isUpWhenOn(stairsModel.isRightUp()) ? 1 : -1));
        double endDuration = endAutoWalker.setSpeed(playerModel.speedLimitX);
        if (blackFilterController != null)
            blackFilterController.blackIn((float) endDuration);
        endAutoWalker.setCallback(() -> goNextFloor());
        playerController.pushAutoWalker(endAutoWalker);

    }

    /**
     * endAutoWalkerのコールバック用. gameManagerに次の階層に行くことを通知する.
     */
    private void goNextFloor() {
        if (gameManagerController == null)
            return;
        StairsModel stairsModel = (StairsModel) model;
        if (GameManagerModel.getFloorChangingState().reverseY().isUpWhenOn(stairsModel.isRightUp()))
            gameManagerController.nextFloor();
        else
            gameManagerController.prevFloor();
    }
}
