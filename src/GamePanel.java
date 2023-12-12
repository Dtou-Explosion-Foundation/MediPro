import java.awt.Graphics;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.JPanel;

import objects.base.GameObject.GameObjectController;

class GamePanel extends JPanel {
    // Image img =
    // Toolkit.getDefaultToolkit().getImage("img/background_sample.png");

    public ArrayList<GameObjectController> controllers;

    Duration deltaTime = Duration.ZERO;
    Instant beginTime = Instant.now();

    final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public GamePanel() {
        super();
        logger.info("Init GamePanel");
        controllers = new ArrayList<GameObjectController>();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (GameObjectController controller : controllers) {
            controller.update(deltaTime.toNanos() / 1000000000f);
        }
        for (GameObjectController controller : controllers) {
            controller.draw(g);
        }
        Instant currentTime = Instant.now();
        deltaTime = Duration.between(beginTime, currentTime);
        beginTime = currentTime;
    }
}