package medipro.object.base.gameobject;

import java.awt.geom.AffineTransform;
import java.util.logging.Logger;

import medipro.object.base.World;

/**
 * ゲームオブジェクトのモデルクラス. オブジェクトのワールド内での位置や回転、拡大率などを保持する.
 */
public abstract class GameObjectModel {
    /**
     * ロガー.
     */
    protected final Logger logger = Logger.getLogger(this.getClass().getName());
    /**
     * ゲームオブジェクトが存在するワールド.
     */
    public World world;

    /**
     * オブジェクトのx座標.
     */
    public double x;
    /**
     * オブジェクトのy座標.
     */
    public double y;
    /**
     * オブジェクトの回転度.
     */
    public double rotation;
    /**
     * オブジェクトのX座標方向のスケール.
     */
    public double scaleX;
    /**
     * オブジェクトのY座標方向のスケール.
     */
    public double scaleY;

    public double interval;

    public double deltaX;
    public double deltaY;
    public double delta2X;
    public double delta2Y;

    public int timesX;
    public int timesY;

    /**
     * ゲームオブジェクトのモデルを生成する.
     * 
     * @param world モデルが存在するワールド
     */
    public GameObjectModel(World world) {
        this.world = world;

        this.x = 0;
        this.y = 0;

        this.rotation = 0;

        this.scaleX = 1;
        this.scaleY = 1;

        this.interval = 1;

        this.deltaX = 0;
        this.deltaY = 0;
        this.delta2X = 0;
        this.delta2Y = 0;

        this.timesX = 0;
        this.timesY = 0;
    }

    /**
     * グローバル座標からローカル座標に変換するアフィン変換行列を取得する
     * 
     * @return AffineTransform
     */
    public AffineTransform getTransformMatrix() {
        AffineTransform transform = new AffineTransform();
        transform.translate(x, -y);
        transform.rotate(rotation);
        transform.scale(scaleX, scaleY);
        return transform;
    }

}
