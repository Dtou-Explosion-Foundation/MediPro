package medipro.gui.panel;

import java.util.logging.Logger;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;

import medipro.config.EngineConfig;
import medipro.config.InGameConfig;
import medipro.gui.frame.GameFrame;
import medipro.object.base.World;
import medipro.object.manager.gamemanager.GameManagerModel;
import medipro.world.PlayWorld;
import medipro.world.TitleMenuWorld;

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
    public GameFrame frame;

    /**
     * ゲームのパネルを生成する.
     * 
     * @param frame        パネルが配置されたゲームのウインドウ
     * @param capabilities OpenGLの設定
     */
    public GLGamePanel(GameFrame frame, GLCapabilities capabilities) {
        super(capabilities);
        logger.info("Init GLGamePanel");
        this.addGLEventListener(this);
        this.frame = frame;
        world = EngineConfig.SKIP_TITLE ? new PlayWorld(this) : new TitleMenuWorld(this);
    }

    @Override
    public GameFrame getFrame() {
        return frame;
    }

    @Override
    public boolean shouldInvokeUpdate() {
        return false;
    }

    /**
     * 前回のアップデートの時間を取得する.
     */
    private long lastRepaintTime = -1;

    /**
     * 前回のアップデートからの経過時間を取得する.
     * 
     * @return 経過時間
     */
    private double getDeltaTime() {
        long currentTime = System.nanoTime();
        long deltaTime = lastRepaintTime == -1 ? 0 : currentTime - lastRepaintTime;
        lastRepaintTime = currentTime;
        return deltaTime / 1000000000.0 * InGameConfig.GAME_SPEED * GameManagerModel.getPause();
    }

    @Override
    public void update(double deltaTime) {
        world.update(deltaTime);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        if (InGameConfig.USE_OPENGL) {
            this.update(this.getDeltaTime());
            if (needInitialize == true) {
                needInitialize = false;
                world.init(drawable);
            }
            GL4 gl = drawable.getGL().getGL4();
            gl.glClear(GL4.GL_COLOR_BUFFER_BIT);
            world.display(drawable);
        }
    }

    public static GLCapabilities getGlCapabilities() {
        final GLProfile profile = GLProfile.get(GLProfile.GL4);
        return new GLCapabilities(profile);
    }

    /**
     * 初期化が必要かどうか.ワールドの入れ替え時、{@code init(GLAutoDrawable)}が実行されないため使用.
     */
    private boolean needInitialize = true;

    @Override
    public void dispose(GLAutoDrawable drawable) {
        if (InGameConfig.USE_OPENGL)
            world.dispose(drawable);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        if (InGameConfig.USE_OPENGL) {
            needInitialize = false;
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

    @Override
    public void setWorld(World world) {
        needInitialize = true;
        this.world = world;
    }

}