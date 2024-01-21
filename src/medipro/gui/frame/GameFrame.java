package medipro.gui.frame;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

import medipro.config.EngineConfig;
import medipro.config.InGameConfig;
import medipro.gui.panel.G2dGamePanel;
import medipro.gui.panel.GLGamePanel;
import medipro.gui.panel.IGamePanel;
import medipro.object.manager.gamemanager.GameManagerModel;

/**
 * ゲームのウインドウを実装するクラス.
 */
public class GameFrame extends JFrame implements ComponentListener {
    /**
     * ロガー.
     */
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * 現在のモニター.
     */
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

        final GLProfile profile = GLProfile.get(GLProfile.GL4);
        GLCapabilities capabilities = new GLCapabilities(profile);

        JPanel panel = InGameConfig.USE_OPENGL ? new GLGamePanel(this, capabilities) : new G2dGamePanel(this);
        panel.setFocusable(true);
        panel.setPreferredSize(new Dimension(width, height));
        this.add(panel);
        this.pack();

        this.setLocation(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[EngineConfig.DEFAULT_MONITOR]
                        .getDefaultConfiguration().getBounds().getLocation());
        logger.info("Current monitor: " + currentGraphicsDevice.getIDstring() + "(x" + getScreenScaleFactor() + ")");

        this.addComponentListener(this);

        logger.log(Level.FINE, "Frame size: " + this.getSize());
        logger.log(Level.FINE, "Panel size: " + panel.getSize());

        {
            Timer timer = new Timer(1000 / InGameConfig.FPS, new ActionListener() {
                long lastRepaintTime = -1;

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (panel instanceof IGamePanel && ((IGamePanel) panel).shouldInvokeUpdate()) {
                        long currentTime = System.nanoTime();
                        long deltaTime = lastRepaintTime == -1 ? 0 : currentTime - lastRepaintTime;
                        lastRepaintTime = currentTime;
                        ((IGamePanel) panel).update(deltaTime / 1000000000.0 * InGameConfig.GAME_SPEED * GameManagerModel.getPause());
                    }
                    repaint();
                }
            });
            timer.start();
        }
    }

    /**
     * 現在のモニターを取得する.
     * 
     * @return 現在のモニター
     */
    public GraphicsDevice getCurrentGraphicsDevice() {
        return currentGraphicsDevice;
    }

    /**
     * 現在のモニターのスケールファクターを取得する.
     * 
     * @return スケールファクター
     */
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

            if (bounds.contains(this.getLocation()) && currentGraphicsDevice != device) {
                currentGraphicsDevice = device;
                logger.info("Current monitor: " + device.getIDstring() + "(x" + getScreenScaleFactor() + ")");
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