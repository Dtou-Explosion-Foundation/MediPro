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
     * @param model 対象のモデル
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

    @Override
    protected boolean needUpdateTexture() {
        return false;
    }

    @Override
    protected void initNames() {
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        this.initNames();
        shaderProgram = 0;
        this.initBuffers(drawable);
    }

    @Override
    protected void initBuffers(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glUseProgram(shaderProgram);
        IntBuffer ubo = Buffers.newDirectIntBuffer(1);

        CameraModel cameraModel = (CameraModel) model;

        gl.glGenBuffers(1, ubo);

        Matrix4f tempMat = new Matrix4f();
        FloatBuffer cameraMatBuffer = FloatBuffer.allocate(4 * 4 * 2)
                .put(tempMat.get(FloatBuffer.allocate(4 * 4)).flip())
                .put(tempMat.get(FloatBuffer.allocate(4 * 4)).flip()).flip();

        gl.glBindBuffer(GL4.GL_UNIFORM_BUFFER, ubo.get(0));
        gl.glBufferData(GL4.GL_UNIFORM_BUFFER, Float.BYTES * cameraMatBuffer.limit(), cameraMatBuffer,
                GL4.GL_DYNAMIC_DRAW);

        cameraModel.setUBO(ubo.get(0));
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glUseProgram(shaderProgram);

        updateUniforms(drawable);
    }

    @Override
    protected void updateUniforms(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        CameraModel cameraModel = (CameraModel) model;
        int ubo = cameraModel.getUBO();

        Matrix4f tempMat = new Matrix4f();
        Matrix4f projMat = new Matrix4f().transpose();
        Matrix4f viewMat = new Matrix4f() // カメラ中心の座標に変換
                .scale((float) cameraModel.getScale(), (float) cameraModel.getScale(), 1, tempMat) // カメラのズーム倍率を適用
                .scale((float) (2.0 / InGameConfig.WINDOW_WIDTH_BASE), (float) (2.0 / InGameConfig.WINDOW_HEIGHT_BASE),
                        1, tempMat) // -1~1をウインドウサイズに変換
                // .scale(1, -1, 1, tempMat) // 上下反転
                .translate((float) -cameraModel.x, (float) -cameraModel.y, 0, tempMat);

        FloatBuffer cameraMatBuffer = FloatBuffer.allocate(4 * 4 * 2)
                .put(projMat.get(FloatBuffer.allocate(4 * 4)).flip())
                .put(viewMat.get(FloatBuffer.allocate(4 * 4)).flip()).flip();

        gl.glBindBuffer(GL4.GL_UNIFORM_BUFFER, ubo);
        gl.glBufferSubData(GL4.GL_UNIFORM_BUFFER, 0, Float.BYTES * cameraMatBuffer.limit(), cameraMatBuffer);
    }

}
