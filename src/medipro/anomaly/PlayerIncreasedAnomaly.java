package medipro.anomaly;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.player.PlayerModel;

/**
 * 表示されるプレイヤーを前後に増加させる異変のコントローラ
 */
public class PlayerIncreasedAnomaly extends GameObjectController implements AnomalyListener {

    /**
     * 表示されるプレイヤーを前後に増加させる異変のコントローラの生成
     * @param model 対象のモデル
     */
    public PlayerIncreasedAnomaly(GameObjectModel model) {
        super(model);
    }

    @Override
    public boolean canAnomalyOccurred() {
        return true;
    }

    @Override
    public void onAnomalyOccurred(int level) {
        if (model instanceof PlayerModel)
            ((PlayerModel) model).isDummies = true;
    }

    @Override
    public void onAnomalyFinished() {
        if (model instanceof PlayerModel)
            ((PlayerModel) model).isDummies = false;
    }

    @Override
    public int minAnomalyLevel() {
        return 0;
    }

    @Override
    public int maxAnomalyLevel() {
        return 0;
    }

    private int occurredChance = 1;

    public void setOccurredChance(int chance) {
        occurredChance = chance;
    }

    @Override
    public int getOccurredChance() {
        return occurredChance;
    }

    @Override
    public void update(double dt) {

    }

}
