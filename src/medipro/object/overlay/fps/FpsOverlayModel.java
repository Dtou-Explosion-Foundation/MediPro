package medipro.object.overlay.fps;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

public class FpsOverlayModel extends GameObjectModel {
    Queue<Integer> fpsHistory;
    int fpsSum;
    static final int queueSize = 24;

    public FpsOverlayModel(World world) {
        super(world);
        fpsHistory = new LinkedBlockingQueue<Integer>(queueSize);
        for (int index = 0; index < queueSize; index++) {
            fpsHistory.add(0);
        }
    }

    public void updateFpsHistory(int newFps) {
        fpsSum -= fpsHistory.poll();
        fpsSum += newFps;
        fpsHistory.add(newFps);
    }

    public int getFps() {
        return fpsSum / queueSize;
    }
}
