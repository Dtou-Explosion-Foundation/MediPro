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
        fpsSum -= fpsHistory.poll();
        fpsSum += newFps;
        fpsHistory.add(newFps);
    }

    public int getFps() {
        return fpsSum / queueSize;
    }
}
