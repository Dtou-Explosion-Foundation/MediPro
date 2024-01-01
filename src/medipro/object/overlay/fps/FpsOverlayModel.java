package medipro.object.overlay.fps;

import java.awt.Color;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import medipro.config.InGameConfig;
import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

public class FpsOverlayModel extends GameObjectModel {
    Queue<Short> fpsHistory;
    private int fpsSum = 0;
    static final int queueSize = (int) (0.1 * InGameConfig.FPS);
    // static final int queueSize = 100 * ( InGameConfig.FPS / 60 );

    private Color color = Color.WHITE;

    public FpsOverlayModel(World world) {
        super(world);
        fpsHistory = new LinkedBlockingQueue<Short>(queueSize);
        for (int index = 0; index < queueSize; index++) {
            fpsHistory.add((short) 0);
        }
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void updateFpsHistory(short newFps) {
        // logger.info("newFps: " + newFps);
        // logger.info(" - fpsSum: " + fpsSum);
        fpsSum -= fpsHistory.poll();
        // logger.info(" - fpsSum1: " + fpsSum);
        fpsSum += newFps;
        // logger.info(" - fpsSum2: " + fpsSum);
        fpsHistory.add(newFps);
    }

    public int getFps() {
        // logger.info("fps: " + fpsSum / queueSize);
        return fpsSum / queueSize;
    }
}
