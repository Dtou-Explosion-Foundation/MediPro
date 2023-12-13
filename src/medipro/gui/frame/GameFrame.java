package medipro.gui.frame;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.swing.JFrame;

import medipro.config.Config;
import medipro.gui.panel.GamePanel;

public class GameFrame extends JFrame {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    public GameFrame(String title, int width, int height) {
        super(title);
        logger.info("Init GameFrame");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        GamePanel panel = new GamePanel(this);
        panel.setFocusable(true);
        // setupWorld(panel);
        this.add(panel);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            Boolean isIdle = true;

            @Override
            public void run() {
                if (isIdle) {
                    isIdle = false;
                    panel.repaint();
                    isIdle = true;
                } else {
                    logger.warning("Game is running slow");
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, (long) (1000f / Config.FPS));
    }
}