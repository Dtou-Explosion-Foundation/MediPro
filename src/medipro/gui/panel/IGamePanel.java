package medipro.gui.panel;

import medipro.gui.frame.GameFrame;
import medipro.object.base.World;

/**
 * ゲームパネルのインターフェース.
 */
public interface IGamePanel {
    /**
     * パネルを更新する.
     * 
     * @param deltaTime 前回の更新からの経過時間
     */
    public void update(double deltaTime);

    /**
     * パネルが格納されているフレームを取得する.
     * 
     * @return フレーム
     */
    public GameFrame getFrame();

    /**
     * {@code update(double)}をGameFrameから呼び出す必要があるかどうか. drawなどに紐付いており、独自の実装をする必要がある時のためのフラグ.
     * 
     * @return フレーム
     */
    public boolean shouldInvokeUpdate();

    /**
     * パネルにワールドを設定する.
     * 
     * @param world ワールド
     */
    public void setWorld(World world);
}
