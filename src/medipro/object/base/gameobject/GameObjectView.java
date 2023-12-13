package medipro.object.base.gameobject;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * ゲームオブジェクトのビュークラス。 複数のモデルを保持し、順に描画することができる。
 */
public abstract class GameObjectView {
    /**
     * ロガー.
     */
    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    /**
     * 格納しているモデル.
     */
    public ArrayList<GameObjectModel> models;

    /**
     * ゲームオブジェクトビューを生成する。
     */
    public GameObjectView() {
        this.models = new ArrayList<GameObjectModel>();
    }

    /**
     * ゲームオブジェクトビューを生成する。
     * 
     * @param models 格納するモデル
     */
    public GameObjectView(GameObjectModel... models) {
        this();
        for (GameObjectModel model : models) {
            this.models.add(model);
        }
    }

    /**
     * 格納しているモデルに対してそれぞれdraw()を呼び出す。
     * 
     * @param g               描画対象のGraphics2D
     * @param cameraTransform カメラ座標へ変換するためのアフィン変換行列
     */
    public void drawModels(Graphics2D g, AffineTransform cameraTransform) {

        for (GameObjectModel model : models) {
            g.setTransform(cameraTransform);
            this.draw(model, g);
        }
    }

    /**
     * モデルを元に描画を行う
     * 
     * @param model 描画対象のモデル
     * @param g     描画対象のGraphics2D
     */
    abstract public void draw(GameObjectModel model, Graphics2D g);
}
