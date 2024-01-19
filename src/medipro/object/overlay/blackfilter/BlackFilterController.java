package medipro.object.overlay.blackfilter;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

public class BlackFilterController extends GameObjectController {

    public BlackFilterController(GameObjectModel model) {
        super(model);
    }

    private byte state = 0; // 0: none, 1: blackIn, -1: blackOut

    @Override
    public void update(double dt) {
        BlackFilterModel model = (BlackFilterModel) this.model;
        float duration = model.getDuration();
        if (state != 0 && model.addAlpha(dt * state / duration))
            state = 0;
    }

    public void blackIn(float duration) {
        BlackFilterModel model = (BlackFilterModel) this.model;
        state = 1;
        model.setDuration(duration);
    }

    public void blackOut(float duration) {
        BlackFilterModel model = (BlackFilterModel) this.model;
        state = -1;
        model.setDuration(duration);
    }

    public void setAlpha(float alpha) {
        ((BlackFilterModel) model).setAlpha(alpha);
        state = 0;
    }
}
