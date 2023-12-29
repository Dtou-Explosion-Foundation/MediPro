package medipro.gui.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import medipro.object.base.World;
import medipro.world.TestWorld;
import medipro.world.TitleMenuWorld;
import medipro.object.base.World;

/**
 * ゲームのパネルを実装するクラス.
 */
public class GamePanel extends JPanel {

    /**
     * パネルの子ワールド.
     */
    public World world;

    /**
     * 前回のフレームからの経過時間.
     */
    Duration deltaTime = Duration.ZERO;

    /**
     * 前回のフレームの時間.
     */
    Instant prevTime = Instant.now();

    /**
     * ロガー.
     */
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * パネルの親ウインドウ.
     */
    public JFrame frame;

    /**
     * ゲームのパネルを生成する.
     * 
     * @param frame パネルが配置されたゲームのウインドウ
     */
    public GamePanel(JFrame frame) {
        super();
        logger.info("Init GamePanel");
        this.frame = frame;
        // world = new TestWorld(this);
        world = new TitleMenuWorld(this);
    }

    public void setWorld(World world){
        this.world = world;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // g2.setRenderingHint(RenderingHints.KEY_RENDERING,
        // RenderingHints.VALUE_RENDER_QUALITY);
        // g2.setRenderingHint(RenderingHints.KEY_RENDERING,
        // RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        // g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        // RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        world.updateAndDraw(g2, deltaTime.toNanos() / 1000000000f);
        Instant currentTime = Instant.now();
        deltaTime = Duration.between(prevTime, currentTime);
        prevTime = currentTime;
    }
}