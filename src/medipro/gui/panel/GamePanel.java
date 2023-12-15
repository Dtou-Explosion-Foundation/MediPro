package medipro.gui.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import medipro.world.TestWorld;
import medipro.world.World;

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
        world = new TestWorld(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        world.updateAndDraw((Graphics2D) g, deltaTime.toNanos() / 1000000000f);
        Instant currentTime = Instant.now();
        deltaTime = Duration.between(prevTime, currentTime);
        prevTime = currentTime;
    }
}