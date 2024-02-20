package medipro.object.overlay.fps;

import java.awt.Color;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import medipro.config.InGameConfig;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

/**
 * FPSをオーバーレイ表示するモデル.
 */
public class FpsOverlayModel extends GameObjectModel {
    /**
     * fpsの履歴.
     */
    private Queue<Short> fpsHistory;
    /**
     * 履歴に含まれるfpsの合計.
     */
    private int fpsSum = 0;
    /**
     * 履歴のサイズ.
     */
    static final int QUEU_SIZE = (int) (0.1 * InGameConfig.FPS);
    /**
     * 文字の色.
     */
    private Color color = Color.WHITE;

    /**
     * FPSをオーバーレイ表示するモデルを生成する.
     * 
     * @param world オブジェクトが存在するワールド
     */
    public FpsOverlayModel(World world) {
        super(world);
        fpsHistory = new LinkedBlockingQueue<Short>(QUEU_SIZE);
        for (int index = 0; index < QUEU_SIZE; index++) {
            fpsHistory.add((short) 0);
        }
    }

    /**
     * 文字の色を取得する.
     * 
     * @return 文字の色
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * 文字の色を設定する.
     * 
     * @param color 文字の色
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * fpsの履歴を更新する.
     * 
     * @param newFps 新しいfps
     */
    public void updateFpsHistory(short newFps) {
        fpsSum -= fpsHistory.poll();
        fpsSum += newFps;
        fpsHistory.add(newFps);
    }

    /**
     * fpsの平均値を取得する.
     * 
     * @return fpsの平均値
     */
    public int getFps() {
        return fpsSum / QUEU_SIZE;
    }
}
