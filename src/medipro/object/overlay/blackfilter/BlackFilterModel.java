package medipro.object.overlay.blackfilter;

import java.awt.Color;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

/**
 * 暗転のモデル.
 */
public class BlackFilterModel extends GameObjectModel {

    /**
     * 暗転のモデルを生成する.
     * 
     * @param world 対象のワールド
     */
    public BlackFilterModel(World world) {
        super(world);
    }

    /**
     * 透明度.
     */
    private float alpha = 0;
    /**
     * 変化にかかる時間.
     */
    private float duration = 0;

    /**
     * 変化にかかる時間を設定する.
     * 
     * @return 変化にかかる時間
     */
    public float getDuration() {
        return duration;
    }

    /**
     * 変化にかかる時間を設定する.
     * 
     * @param duration 変化にかかる時間
     */
    public void setDuration(float duration) {
        this.duration = duration;
    }

    /**
     * 透明度を0から1の間に制限する.
     * 
     * @return 透明度が制限されたかどうか
     */
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

    /**
     * 透明度を取得する.
     * 
     * @return 透明度
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * 透明度を設定する.
     * 
     * @param alpha 透明度
     * @return 透明度が制限されたかどうか
     */
    public boolean setAlpha(float alpha) {
        this.alpha = alpha;
        return clampAlpha();
    }

    /**
     * 透明度を加算する.
     * 
     * @param alpha 透明度
     * @return 透明度が制限されたかどうか
     */
    public boolean addAlpha(double alpha) {
        this.alpha += alpha;
        return clampAlpha();
    }

    /**
     * ブラックフィルタの色.
     */
    public enum BlackFilterColor {
        /** 黒. */
        BLACK,
        /** 赤. */
        RED
    }

    /**
     * ブラックフィルタの色.
     */
    public BlackFilterColor color = BlackFilterColor.BLACK;

    /**
     * ブラックフィルタの色を設定する.
     * 
     * @param color ブラックフィルタの色
     */
    public void setColor(BlackFilterColor color) {
        this.color = color;
    }

    /**
     * ブラックフィルタの色を取得する.
     * 
     * @return ブラックフィルタの色
     */
    public Color getColor() {
        switch (color) {
        case BLACK:
            return new Color(0f, 0f, 0f, alpha);
        case RED:
            return new Color(1f, 0f, 0f, alpha);
        default:
            return new Color(0f, 0f, 0f, alpha);
        }
    }
}
