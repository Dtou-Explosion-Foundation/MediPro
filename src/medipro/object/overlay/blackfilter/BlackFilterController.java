package medipro.object.overlay.blackfilter;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * 暗転のコントローラ.
 */
public class BlackFilterController extends GameObjectController {

    /**
     * 暗転のコントローラを生成する.
     * 
     * @param model 対象のモデル
     */
    public BlackFilterController(GameObjectModel model) {
        super(model);
    }

    /**
     * 変化の状態を表す. 0: none, 1: blackIn, -1: blackOut
     */
    private byte state = 0;

    @Override
    public void update(double dt) {
        BlackFilterModel model = (BlackFilterModel) this.model;
        float duration = model.getDuration();
        if (state != 0 && model.addAlpha(dt * state / duration))
            state = 0;
    }

    /**
     * 暗転する.
     * 
     * @param duration 暗転にかかる時間
     */
    public void blackIn(float duration) {
        BlackFilterModel model = (BlackFilterModel) this.model;
        state = 1;
        model.setDuration(duration);
    }

    /**
     * 明転する.
     * 
     * @param duration 明転にかかる時間
     */
    public void blackOut(float duration) {
        BlackFilterModel model = (BlackFilterModel) this.model;
        state = -1;
        model.setDuration(duration);
    }

    /**
     * 透明度を設定する.暗転や明転は中断される.
     * 
     * @param alpha 透明度
     */
    public void setAlpha(float alpha) {
        ((BlackFilterModel) model).setAlpha(alpha);
        state = 0;
    }
}
