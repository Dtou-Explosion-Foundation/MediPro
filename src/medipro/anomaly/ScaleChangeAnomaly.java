package medipro.anomaly;

import java.util.ArrayList;

import medipro.object.AnomalyListener;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * 静的にスケールが変化する異変.
 */
public class ScaleChangeAnomaly extends GameObjectController implements AnomalyListener {

    /**
     * スケールの変化量の候補.
     */
    private ArrayList<Double> scaleList = new ArrayList<Double>();

    /**
     * スケールの変化量の候補を設定する.
     * 
     * @param scaleList スケールの変化量の候補
     */
    public void setScaleList(ArrayList<Double> scaleList) {
        this.scaleList = scaleList;
    }

    /**
     * デフォルトのスケール.元に戻すために使用する.
     */
    private double defaultScaleX = 1.0;
    /**
     * デフォルトのスケール.元に戻すために使用する.
     */
    private double defaultScaleY = 1.0;

    /**
     * スケールの変化軸.
     */
    private Axis axis = Axis.BOTH;

    /**
     * スケールの変化軸を取得する.
     * 
     * @return スケールの変化軸
     */
    public Axis getAxis() {
        return axis;
    }

    /**
     * スケールの変化軸を設定する.
     * 
     * @param axis スケールの変化軸
     */
    public void setAxis(Axis axis) {
        this.axis = axis;
    }

    /**
     * 軸.
     */
    public enum Axis {
        X, Y, BOTH
    }

    /**
     * スケールの変化異変を生成する.
     * 
     * @param model 対象のモデル
     */
    public ScaleChangeAnomaly(GameObjectModel model) {
        super(model);
    }

    @Override
    public boolean canAnomalyOccurred() {
        return !scaleList.isEmpty();
    }

    @Override
    public void onAnomalyOccurred(int level) {
        if (axis == Axis.X || axis == Axis.BOTH) {
            defaultScaleX = model.getScaleX();
            model.multiplyScaleX(scaleList.get(level));
        }
        if (axis == Axis.Y || axis == Axis.BOTH) {
            defaultScaleY = model.getScaleY();
            model.multiplyScaleY(scaleList.get(level));
        }
    }

    @Override
    public void onAnomalyFinished() {
        if (axis == Axis.X || axis == Axis.BOTH) {
            model.setScaleX(defaultScaleX);
        }
        if (axis == Axis.Y || axis == Axis.BOTH) {
            model.setScaleY(defaultScaleY);
        }
    }

    @Override
    public int minAnomalyLevel() {
        return 0;
    }

    @Override
    public int maxAnomalyLevel() {
        return scaleList.size() - 1;
    }

    /**
     * 発生確率.
     */
    private int occurredChance = 1;

    /**
     * 発生確率を取得する.
     * 
     * @return 発生確率
     */
    public void setOccurredChance(int occurredChance) {
        this.occurredChance = occurredChance;
    }

    @Override
    public int getOccurredChance() {
        return occurredChance;
    }

    @Override
    public void update(double dt) {
    }

}
