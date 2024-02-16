package medipro.gui.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.logging.Logger;

import javax.swing.JPanel;

import medipro.config.EngineConfig;
import medipro.config.InGameConfig;
import medipro.gui.frame.GameFrame;
import medipro.object.base.World;
import medipro.object.manager.gamemanager.GameManagerModel;
import medipro.world.PlayWorld;
import medipro.world.TitleMenuWorld;

/**
 * Graphics2Dで描画されるゲームのパネルを実装するクラス.
 */
public class GamePanel extends JPanel {

    /**
     * パネルの子ワールド.
     */
    public World world;

    /**
     * ロガー.
     */
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * パネルの親ウインドウ.
     */
    public GameFrame frame;

    /**
     * ゲームのパネルを生成する.
     * 
     * @param frame パネルが配置されたゲームのウインドウ
     */
    public GamePanel(GameFrame frame) {
        super();
        logger.info("Init GamePanel");
        this.frame = frame;
        world = EngineConfig.SKIP_TITLE ? new PlayWorld(this) : new TitleMenuWorld(this);
    }

    /**
     * パネルを更新する.
     * 
     * @param deltaTime 前回の更新からの経過時間
     */
    public void update(double deltaTime) {
        world.update(deltaTime);
    }

    /**
     * パネルが格納されているフレームを取得する.
     * 
     * @return フレーム
     */
    public GameFrame getFrame() {
        return frame;
    }

    /**
     * 前回のアップデートの時間を取得する.
     */
    private long lastRepaintTime = -1;

    /**
     * 前回のアップデートからの経過時間を取得する.
     * 
     * @return 前回の描画からの経過時間
     */
    private double getDeltaTime() {
        long currentTime = System.nanoTime();
        long deltaTime = lastRepaintTime == -1 ? 0 : currentTime - lastRepaintTime;
        lastRepaintTime = currentTime;
        return deltaTime / 1000000000.0 * InGameConfig.GAME_SPEED * GameManagerModel.getPause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.update(this.getDeltaTime());
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        // g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        // g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        world.draw(g2);
    }

    /**
     * パネルにワールドを設定する.
     * 
     * @param world ワールド
     */
    public void setWorld(World world) {
        setWorld(world, true);
    }

    /**
     * パネルにワールドを設定する.
     * 
     * @param world           ワールド
     * @param disposeOldWorld 古いワールドを破棄するかどうか
     */
    public void setWorld(World world, boolean disposeOldWorld) {
        if (disposeOldWorld)
            this.world.dispose();
        this.world = world;
    }
}