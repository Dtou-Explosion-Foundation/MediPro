import java.util.logging.Logger;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
    final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public GameWindow(String title, int width, int height) {
        super(title);
        logger.info("Init GameWindow");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}