package medipro.anomaly;

import medipro.object.AnomalyListener;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.player.PlayerModel;

/**
 * プレイヤーの動きに関する異変のコントローラー
 */
public class PlayerMovementAnomaly extends GameObjectController implements AnomalyListener {

    private double anomalyResistance = 50;
    private double anomalyAccerelation = 300;

    private double defaultResistance = 600;
    private double defaultAccerelation = 1200;

    /**
     * プレイヤーの動きに関する異変のコントローラーの生成
     * @param model 対象のモデル
     */
    public PlayerMovementAnomaly(GameObjectModel model) {
        super(model);
    }
    /**
     * 
     * @param model 対象のモデル
     * @param anomalyResistance
     * @param anomalyAccerelation
     */
    public PlayerMovementAnomaly(GameObjectModel model, double anomalyResistance, double anomalyAccerelation) {
        super(model);
        this.anomalyResistance = anomalyResistance;
        this.anomalyAccerelation = anomalyAccerelation;
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
