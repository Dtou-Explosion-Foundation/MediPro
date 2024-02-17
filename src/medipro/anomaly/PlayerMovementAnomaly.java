package medipro.anomaly;

import medipro.object.AnomalyListener;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.player.PlayerModel;

/**
 * プレイヤーの移動に異常を発生させるクラス.
 */
public class PlayerMovementAnomaly extends GameObjectController implements AnomalyListener {

    /**
     * 異常時の抵抗値.
     */
    private double anomalyResistance = 50;
    /**
     * 異常時の加速度.
     */
    private double anomalyAccerelation = 300;

    /**
     * デフォルトの抵抗値.
     */
    private double defaultResistance = 0;
    /**
     * デフォルトの加速度.
     */
    private double defaultAccerelation = 0;

    /**
     * PlayerMovementAnomalyを生成する.
     * 
     * @param model
     */
    public PlayerMovementAnomaly(GameObjectModel model) {
        super(model);
    }

    /**
     * PlayerMovementAnomalyを生成する.
     * 
     * @param model        対象のモデル
     * @param resistance   異常時の抵抗値
     * @param accerelation 異常時の加速度
     */
    public PlayerMovementAnomaly(GameObjectModel model, double resistance, double accerelation) {
        super(model);
        this.anomalyResistance = resistance;
        this.anomalyAccerelation = accerelation;
    }

    @Override
    public boolean canAnomalyOccurred() {
        return true;
    }

    @Override
    public void onAnomalyOccurred(int level) {
        PlayerModel playerModel = (PlayerModel) model;
        defaultResistance = playerModel.resitX;
        defaultAccerelation = playerModel.accX;
        playerModel.resitX = anomalyResistance;
        playerModel.accX = anomalyAccerelation;
    }

    @Override
    public void onAnomalyFinished() {
        PlayerModel playerModel = (PlayerModel) model;
        playerModel.resitX = defaultResistance;
        playerModel.accX = defaultAccerelation;
    }

    @Override
    public int minAnomalyLevel() {
        return 0;
    }

    @Override
    public int maxAnomalyLevel() {
        return 0;
    }

    /**
     * 異常発生の確率.
     */
    private int occurredChance = 1;

    /**
     * 異常発生の確率を設定する.
     * 
     * @param chance 異常発生の確率
     */
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
