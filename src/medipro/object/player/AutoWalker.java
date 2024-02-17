package medipro.object.player;

import java.awt.geom.Point2D;
import java.util.function.Function;

/**
 * 自動で移動するための情報を格納するクラス.
 */
public class AutoWalker {
    /**
     * 移動開始地点.
     */
    private Point2D.Double start;

    /**
     * 移動開始地点を返す.
     * 
     * @return 移動開始地点
     */
    public Point2D.Double getStart() {
        return start;
    }

    /**
     * 移動終了地点.
     */
    private Point2D.Double end;

    /**
     * 移動終了地点を返す.
     * 
     * @return 移動終了地点
     */
    public Point2D.Double getEnd() {
        return end;
    }

    /**
     * 移動にかかる時間.
     */
    private double duration;

    /**
     * 補間関数.
     */
    private Function<Double, Double> interpolation;

    /**
     * 移動終了時に呼び出されるコールバック.
     */
    private Runnable callback;

    /**
     * 移動方向. -1: 左, 1: 右
     */
    private byte direction;

    /**
     * 経過時間.
     */
    private double time = 0;

    /**
     * AutoWalkerを生成する.
     * 
     * @param startX 移動開始地点のx座標
     * @param startY 移動開始地点のy座標
     * @param endX   移動終了地点のx座標
     * @param endY   移動終了地点のy座標
     */
    public AutoWalker(double startX, double startY, double endX, double endY) {
        this(new Point2D.Double(startX, startY), new Point2D.Double(endX, endY));
    }

    /**
     * AutoWalkerを生成する.
     * 
     * @param start 移動開始地点
     * @param end   移動終了地点
     */
    public AutoWalker(Point2D.Double start, Point2D.Double end) {
        this.start = start;
        this.end = end;
        duration = 1;
        interpolation = t -> t;
        callback = () -> {
        };
        direction = end.getX() - start.getX() > 0 ? (byte) 1 : (byte) -1;
    }

    /**
     * 移動時間を指定する.
     * 
     * @param duration 移動時間
     */
    public void setDuration(double duration) {
        this.duration = duration;
    }

    /**
     * 移動速度を指定する.
     * 
     * @param speed 移動速度
     * @return 移動時間
     */
    public double setSpeed(double speed) {
        return this.duration = Math.abs(end.getX() - start.getX()) / speed;
    }

    /**
     * 補間関数を指定する.
     * 
     * @param interpolation 補間関数
     */
    public void setInterpolation(Function<Double, Double> interpolation) {
        this.interpolation = interpolation;
    }

    /**
     * 移動終了時に呼び出されるコールバックを指定する.
     * 
     * @param callback コールバック
     */
    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    /**
     * 経過時間を更新する.
     * 
     * @param dt 前フレームからの経過時間
     */
    public void update(double dt) {
        time += dt;
    }

    /**
     * 現在のx座標を返す.
     * 
     * @return 現在のx座標
     */
    public double getNewX() {
        return interpolation.apply(time / duration) * (end.getX() - start.getX()) + start.getX();
    }

    /**
     * 現在のy座標を返す.
     * 
     * @return 現在のy座標
     */
    public double getNewY() {
        return interpolation.apply(time / duration) * (end.getY() - start.getY()) + start.getY();
    }

    /**
     * 移動速度を返す.
     * 
     * @return 移動速度
     */
    public double getSpeed() {
        return (end.getX() - start.getX()) / duration;
    }

    /**
     * 移動が終了したかどうかを返す.
     * 
     * @return 移動が終了したかどうか
     */
    public boolean isFinished() {
        return time > duration;
    }

    /**
     * 移動方向を返す.
     * 
     * @return 移動方向
     */
    public byte getDirection() {
        return direction;
    }

    /**
     * 移動終了時に呼び出されるコールバックを呼び出す.
     */
    public void invokeCallback() {
        callback.run();
    }
}
