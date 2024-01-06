package medipro.object.overlay.blackfilter;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

public class BlackFilterModel extends GameObjectModel {

    public BlackFilterModel(World world) {
        super(world);
    }

    private float alpha = 0;
    private float duration = 0;

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    private boolean clampAlpha() {
        if (alpha < 0) {
            alpha = 0;
            return true;
        }
        if (alpha > 1) {
            alpha = 1;
            return true;
        }
        return false;
    }

    public float getAlpha() {
        return alpha;
    }

    public boolean setAlpha(float alpha) {
        this.alpha = alpha;
        return clampAlpha();
    }

    public boolean addAlpha(double alpha) {
        this.alpha += alpha;
        return clampAlpha();
    }
}
