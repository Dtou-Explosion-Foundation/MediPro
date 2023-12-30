package medipro.gui.frame;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import medipro.config.EngineConfig;
import medipro.config.InGameConfig;
import medipro.gui.panel.G2dGamePanel;
import medipro.gui.panel.GLGamePanel;
import medipro.gui.panel.IGamePanel;

/**
 * ゲームのウインドウを実装するクラス.
 */
public class GameFrame extends JFrame implements ComponentListener {
    /**
     * ロガー.
     */
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    private GraphicsDevice currentGraphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getScreenDevices()[EngineConfig.DEFAULT_MONITOR];

    /**
     * ゲームのウインドウを生成する. 生成後、FPSに基づいてPanelを再描画する.
     * 
     * @param title  ウインドウのタイトル
     * @param width  ウインドウの幅
     * @param height ウインドウの高さ
     */
    public GameFrame(String title, int width, int height) {
        super(title);
        logger.info("Init GameFrame");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        // this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        JPanel panel = InGameConfig.USE_OPENGL ? new GLGamePanel(this) : new G2dGamePanel(this);
        panel.setFocusable(true);
        panel.setPreferredSize(new Dimension(width, height));
        this.add(panel);
        this.pack();

        // if ()
        this.setLocation(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[EngineConfig.DEFAULT_MONITOR]
                        .getDefaultConfiguration().getBounds().getLocation());
        // else
        //     this.setLocationRelativeTo(null);

        this.addComponentListener(this);

        logger.log(Level.FINE, "frame size: " + this.getSize());
        logger.log(Level.FINE, "panel size: " + panel.getSize());

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            Boolean isIdle = true;
            Duration deltaTime = Duration.ZERO;
            Instant prevTime = Instant.now();

            double getDeltaTime() {
                Instant currentTime = Instant.now();
                deltaTime = Duration.between(prevTime, currentTime);
                prevTime = currentTime;
                return deltaTime.toNanos() / 1000000000.0;
            }

            @Override
            public void run() {
                if (isIdle) {
                    isIdle = false;
                    panel.repaint();
                    if (panel instanceof IGamePanel)
                        ((IGamePanel) panel).update(getDeltaTime());
                    isIdle = true;
                } else {
                    logger.warning("Game is running slow");
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, (long) (1000f / InGameConfig.FPS));
    }

    public GraphicsDevice getCurrentGraphicsDevice() {
        return currentGraphicsDevice;
    }

    public double getScreenScaleFactor() {
        return currentGraphicsDevice.getDefaultConfiguration().getDefaultTransform().getScaleX();
    }

    @Override
    public void componentResized(ComponentEvent e) {

    }

    @Override
    public void componentMoved(ComponentEvent e) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screenDevices = ge.getScreenDevices();

        for (GraphicsDevice device : screenDevices) {
            GraphicsConfiguration config = device.getDefaultConfiguration();
            Rectangle bounds = config.getBounds();

            if (bounds.contains(this.getLocation())) {
                currentGraphicsDevice = device;
                break;
            }
        }
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

}