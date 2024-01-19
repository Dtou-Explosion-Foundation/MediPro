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
public class G2dGamePanel extends JPanel implements IGamePanel {

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
    public G2dGamePanel(GameFrame frame) {
        super();
        logger.info("Init GamePanel");
        this.frame = frame;
        world = EngineConfig.SKIP_TITLE ? new PlayWorld(this) : new TitleMenuWorld(this);
    }

    @Override
    public boolean shouldInvokeUpdate() {
        return false;
    }

    @Override
    public void update(double deltaTime) {
        // logger.info("---------- G2dGamePanel::update -----------");
        world.update(deltaTime);
        // logger.info("-----------------------------");
    }

    @Override
    public GameFrame getFrame() {
        return frame;
    }

    private long lastRepaintTime = -1;

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
        if (!InGameConfig.USE_OPENGL) {
            // logger.info("---------- G2dGamePanel::paintComponent -----------");
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // g2.setRenderingHint(RenderingHints.KEY_RENDERING,
            // RenderingHints.VALUE_RENDER_QUALITY);
            // g2.setRenderingHint(RenderingHints.KEY_RENDERING,
            // RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            // g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            // RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            // world.updateAndDraw(g2, this.getDeltaTime());
            world.draw(g2);
            // logger.info("-----------------------------");
        }
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }
}