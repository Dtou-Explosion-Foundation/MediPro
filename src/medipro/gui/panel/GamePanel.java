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

public class GamePanel extends JPanel {
    World world;

    Duration deltaTime = Duration.ZERO;
    Instant beginTime = Instant.now();

    protected final Logger logger = Logger.getLogger(this.getClass().getName());
    JFrame frame;

    public GamePanel(JFrame frame) {
        super();
        logger.info("Init GamePanel");
        this.frame = frame;
        world = new TestWorld(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        world.updateAndDraw((Graphics2D) g, deltaTime.toNanos() / 1000000000f);
        Instant currentTime = Instant.now();
        deltaTime = Duration.between(beginTime, currentTime);
        beginTime = currentTime;
    }
}