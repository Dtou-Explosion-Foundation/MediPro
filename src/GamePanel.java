import java.awt.Graphics;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import worlds.TestWorld;
import worlds.World;

public class GamePanel extends JPanel {
    // Image img =
    // Toolkit.getDefaultToolkit().getImage("img/background_sample.png");

    // public ArrayList<GameObjectController> controllers;

    World world;

    Duration deltaTime = Duration.ZERO;
    Instant beginTime = Instant.now();

    final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    JFrame frame;

    public GamePanel(JFrame frame) {
        super();
        logger.info("Init GamePanel");
        this.frame = frame;
        world = new TestWorld(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        world.updateAndDraw(g, deltaTime.toNanos() / 1000000000f);
        Instant currentTime = Instant.now();
        deltaTime = Duration.between(beginTime, currentTime);
        beginTime = currentTime;
    }
}