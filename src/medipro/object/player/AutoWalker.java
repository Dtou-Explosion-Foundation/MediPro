package medipro.object.player;

import java.awt.geom.Point2D;
import java.util.function.Function;

public class AutoWalker {
    private Point2D.Double start;

    public Point2D.Double getStart() {
        return start;
    }

    private Point2D.Double end;

    public Point2D.Double getEnd() {
        return end;
    }

    private double duration;
    private Function<Double, Double> interpolation;
    private Runnable callback;

    private byte direction;
    private double time = 0;

    public AutoWalker(double startX, double startY, double endX, double endY) {
        this(new Point2D.Double(startX, startY), new Point2D.Double(endX, endY));
    }

    public AutoWalker(Point2D.Double start, Point2D.Double end) {
        this.start = start;
        this.end = end;
        duration = 1;
        interpolation = t -> t;
        callback = () -> {
        };
        direction = end.getX() - start.getX() > 0 ? (byte) 1 : (byte) -1;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double setSpeed(double speed) {
        return this.duration = Math.abs(end.getX() - start.getX()) / speed;
    }

    public void setInterpolation(Function<Double, Double> interpolation) {
        this.interpolation = interpolation;
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    public void update(double dt) {
        time += dt;
    }

    public double getNewX() {
        return interpolation.apply(time / duration) * (end.getX() - start.getX()) + start.getX();
    }

    public double getNewY() {
        return interpolation.apply(time / duration) * (end.getY() - start.getY()) + start.getY();
    }

    public double getSpeed() {
        return (end.getX() - start.getX()) / duration;
    }

    public boolean isFinished() {
        return time > duration;
    }

    public byte getDirection() {
        return direction;
    }

    public void invokeCallback() {
        callback.run();
    }
}