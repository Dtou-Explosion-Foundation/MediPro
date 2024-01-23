package medipro.anomaly;

import java.util.ArrayList;

import medipro.object.AnomalyListener;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

public class ScaleChangeAnomaly extends GameObjectController implements AnomalyListener {

    private ArrayList<Double> scaleList = new ArrayList<Double>();

    public void setScaleList(ArrayList<Double> scaleList) {
        this.scaleList = scaleList;
    }

    private double defaultScaleX = 1.0;
    private double defaultScaleY = 1.0;

    private Axis axis = Axis.BOTH;

    public Axis getAxis() {
        return axis;
    }

    public void setAxis(Axis axis) {
        this.axis = axis;
    }

    public enum Axis {
        X, Y, BOTH
    }

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
            defaultScaleX = model.scaleX;
            model.scaleX *= scaleList.get(level);
        }
        if (axis == Axis.Y || axis == Axis.BOTH) {
            defaultScaleY = model.scaleY;
            model.scaleY *= scaleList.get(level);
        }
    }

    @Override
    public void onAnomalyFinished() {
        if (axis == Axis.X || axis == Axis.BOTH) {
            model.scaleX = defaultScaleX;
        }
        if (axis == Axis.Y || axis == Axis.BOTH) {
            model.scaleY = defaultScaleY;
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

    private int occurredChance = 1;

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
