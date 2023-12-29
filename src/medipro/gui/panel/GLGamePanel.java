package medipro.gui.panel;

import java.util.logging.Logger;

import javax.swing.JFrame;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;

import medipro.config.InGameConfig;
import medipro.object.base.World;
import medipro.world.TestWorld;

/**
 * OpenGLで描画されるゲームのパネルを実装するクラス.
 */
public class GLGamePanel extends GLJPanel implements GLEventListener, IGamePanel {

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
    public GLGamePanel(JFrame frame) {
        super();
        logger.info("Init GLGamePanel");
        this.addGLEventListener(this);
        this.frame = frame;
        world = new TestWorld(this);
    }

    @Override
    public void update(double deltaTime) {
        world.update(deltaTime);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        if (InGameConfig.USE_OPENGL) {
            GL4 gl = drawable.getGL().getGL4();
            gl.glClear(GL4.GL_COLOR_BUFFER_BIT);
            world.display(drawable);
        }
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        if (InGameConfig.USE_OPENGL)
            world.dispose(drawable);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        logger.info("GLGamePanel:init");

        if (InGameConfig.USE_OPENGL) {
            GL4 gl = drawable.getGL().getGL4();
            gl.glEnable(GL4.GL_BLEND);
            gl.glBlendFunc(GL4.GL_SRC_ALPHA, GL4.GL_ONE_MINUS_SRC_ALPHA);
            world.init(drawable);
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        if (InGameConfig.USE_OPENGL)
            world.reshape(drawable, x, y, w, h);
    }
}