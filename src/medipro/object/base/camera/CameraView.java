package medipro.object.base.camera;

import java.awt.Graphics2D;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.math.Matrix4f;

import medipro.config.InGameConfig;
import medipro.object.base.gameobject.GameObjectView;

/**
 * カメラのビューを実装するクラス.
 */
public class CameraView extends GameObjectView {

    /**
     * カメラビューを生成する.
     * 
     * @param model 格納するモデル
     */
    public CameraView(CameraModel model) {
        super(model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g) {
    }

    IntBuffer ubo = Buffers.newDirectIntBuffer(1);

    protected void initBuffers(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        CameraModel cameraModel = (CameraModel) model;

        gl.glGenBuffers(1, ubo);

        Matrix4f tempMat = new Matrix4f();
        Matrix4f projMat = new Matrix4f().transpose();
        // Matrix4f viewMat = new Matrix4f().translate((float) cameraModel.x, (float) cameraModel.y, 0, tempMat);
        logger.info("cameraModel.scale: " + cameraModel.scale);
        Matrix4f viewMat = new Matrix4f() // カメラ中心の座標に変換
                .scale((float) cameraModel.scale, (float) cameraModel.scale, 1, tempMat) // スケーリング
                .scale(1f / InGameConfig.WINDOW_WIDTH, 1f / InGameConfig.WINDOW_HEIGHT, 1, tempMat) // ウインドウサイズに変換
                .scale(1, -1, 1, tempMat) // 上下反転
                .translate((float) -cameraModel.x, (float) cameraModel.y, 0, tempMat);

        FloatBuffer cameraMatBuffer = FloatBuffer.allocate(4 * 4 * 2)
                .put(projMat.get(FloatBuffer.allocate(4 * 4)).flip())
                .put(viewMat.get(FloatBuffer.allocate(4 * 4)).flip()).flip();

        gl.glBindBuffer(GL4.GL_UNIFORM_BUFFER, ubo.get(0));
        gl.glBufferData(GL4.GL_UNIFORM_BUFFER, Float.BYTES * cameraMatBuffer.limit(), cameraMatBuffer,
                GL4.GL_DYNAMIC_DRAW);

        cameraModel.setUBO(ubo.get(0));
    }

    private void updateBuffers(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        CameraModel cameraModel = (CameraModel) model;

        Matrix4f tempMat = new Matrix4f();
        Matrix4f projMat = new Matrix4f().transpose();
        Matrix4f viewMat = new Matrix4f() // カメラ中心の座標に変換
                .scale((float) cameraModel.scale, (float) cameraModel.scale, 1, tempMat) // スケーリング
                .scale(1f / InGameConfig.WINDOW_WIDTH, 1f / InGameConfig.WINDOW_HEIGHT, 1, tempMat) // ウインドウサイズに変換
                .scale(1, -1, 1, tempMat) // 上下反転
                .translate((float) -cameraModel.x, (float) cameraModel.y, 0, tempMat);

        FloatBuffer cameraMatBuffer = FloatBuffer.allocate(4 * 4 * 2)
                .put(projMat.get(FloatBuffer.allocate(4 * 4)).flip())
                .put(viewMat.get(FloatBuffer.allocate(4 * 4)).flip()).flip();

        gl.glBindBuffer(GL4.GL_UNIFORM_BUFFER, ubo.get(0));
        gl.glBufferSubData(GL4.GL_UNIFORM_BUFFER, 0, Float.BYTES * cameraMatBuffer.limit(), cameraMatBuffer);

        cameraModel.setUBO(ubo.get(0));
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        updateBuffers(drawable);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void init(GLAutoDrawable drawable) {
        initBuffers(drawable);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {

    }

}
