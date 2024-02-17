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
    private World world;

    /**
     * ワールドを取得する.
     * 
     * @return ワールド
     */
    public World getWorld() {
        return world;
    }

    /**
     * ワールドを設定する.
     * 
     * @param world ワールド
     */
    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * オブジェクトのx座標.
     */
    private double x;

    /**
     * オブジェクトのx座標を取得する.
     * 
     * @return x座標
     */
    public double getX() {
        return x;
    }

    /**
     * オブジェクトのx座標を設定する.
     * 
     * @param x x座標
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * オブジェクトのx座標に加算する.
     * 
     * @param x x座標
     */
    public void addX(double x) {
        this.x += x;
    }

    /**
     * オブジェクトのy座標.
     */
    private double y;

    /**
     * オブジェクトのy座標を取得する.
     * 
     * @return y座標
     */
    public double getY() {
        return y;
    }

    /**
     * オブジェクトのy座標を設定する.
     * 
     * @param y y座標
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * オブジェクトのy座標に加算する.
     * 
     * @param y y座標
     */
    public void addY(double y) {
        this.y += y;
    }

    /**
     * オブジェクトの回転度.
     */
    private double rotation;

    /**
     * オブジェクトの回転度を取得する.
     * 
     * @return 回転度
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * オブジェクトの回転度を設定する.
     * 
     * @param rotation 回転度
     */
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    /**
     * オブジェクトのX座標方向のスケール.
     */
    private double scaleX;

    /**
     * オブジェクトのX座標方向のスケールを取得する.
     * 
     * @return X座標方向のスケール
     */
    public double getScaleX() {
        return scaleX;
    }

    /**
     * オブジェクトのX座標方向のスケールを設定する.
     * 
     * @param scaleX X座標方向のスケール
     */
    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    /**
     * オブジェクトのX座標方向のスケールに加算する.
     * 
     * @param scaleX X座標方向のスケール
     */
    public void addScaleX(double scaleX) {
        this.scaleX += scaleX;
    }

    /**
     * オブジェクトのX座標方向のスケールに乗算する.
     * 
     * @param scaleX X座標方向のスケール
     */
    public void multiplyScaleX(double scaleX) {
        this.scaleX *= scaleX;
    }

    /**
     * オブジェクトのY座標方向のスケール.
     */
    private double scaleY;

    /**
     * オブジェクトのY座標方向のスケールを取得する.
     * 
     * @return Y座標方向のスケール
     */
    public double getScaleY() {
        return scaleY;
    }

    /**
     * オブジェクトのY座標方向のスケールを設定する.
     * 
     * @param scaleY Y座標方向のスケール
     */
    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }

    /**
     * オブジェクトのY座標方向のスケールに加算する.
     * 
     * @param scaleY Y座標方向のスケール
     */
    public void addScaleY(double scaleY) {
        this.scaleY += scaleY;
    }

    /**
     * オブジェクトのY座標方向のスケールに乗算する.
     * 
     * @param scaleY Y座標方向のスケール
     */
    public void multiplyScaleY(double scaleY) {
        this.scaleY *= scaleY;
    }

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

    }

    /**
     * グローバル座標からローカル座標に変換するアフィン変換行列を取得する.
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
