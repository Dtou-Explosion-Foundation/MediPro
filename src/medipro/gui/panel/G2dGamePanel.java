package medipro.gui.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import medipro.config.InGameConfig;
import medipro.object.base.World;
import medipro.world.TestWorld;

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
    public JFrame frame;

    /**
     * ゲームのパネルを生成する.
     * 
     * @param frame パネルが配置されたゲームのウインドウ
     */
    public G2dGamePanel(JFrame frame) {
        super();
        logger.info("Init GamePanel");
        this.frame = frame;
        world = new TestWorld(this);
    }

    @Override
    public void update(double deltaTime) {
        world.update(deltaTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!InGameConfig.USE_OPENGL) {
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
        }
    }
}