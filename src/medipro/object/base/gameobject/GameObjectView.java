package medipro.object.base.gameobject;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.logging.Logger;

import medipro.config.InGameConfig;

/**
 * ゲームオブジェクトのビュークラス.
 */
public abstract class GameObjectView {
    /**
     * ロガー.
     */
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * 格納しているモデル.
     */
    public GameObjectModel model;

    /**
     * ゲームオブジェクトビューを生成する。
     * 
     * @param model 対象のモデル
     */
    public GameObjectView(GameObjectModel model) {
        this.model = model;
    }

    /**
     * 格納しているモデルに対してそれぞれdraw()を呼び出す。
     * 
     * @param g               描画対象のGraphics2D
     * @param cameraTransform カメラ座標へ変換するためのアフィン変換行列
     */
    public void draw(Graphics2D g, AffineTransform cameraTransform) {
        g.setTransform(cameraTransform);
        g.transform(model.getTransformMatrix());
        this.draw(g);
    }

    /**
     * モデルを元に描画を行う
     * 
     * @param g 描画対象のGraphics2D
     */
    protected abstract void draw(Graphics2D g);

    /**
     * スプライトの幅を取得する.デフォルトではカメラが表示する範囲の幅を返す.
     * 
     * @return スプライトの幅
     */
    protected float getSpriteWidth() {
        float cameraScale = this.model.world.camera.isPresent() ? (float) this.model.world.camera.get().getScale() : 1f;
        return InGameConfig.WINDOW_WIDTH_BASE / cameraScale;
    }

    /**
     * スプライトの高さを取得する.デフォルトではカメラが表示する範囲の高さを返す.
     * 
     * @return スプライトの高さ
     */
    protected float getSpriteHeight() {
        float cameraScale = this.model.world.camera.isPresent() ? (float) this.model.world.camera.get().getScale() : 1f;
        return InGameConfig.WINDOW_HEIGHT_BASE / cameraScale;
    }

}
