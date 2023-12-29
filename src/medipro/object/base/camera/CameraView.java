package medipro.object.base.camera;

import java.awt.Graphics2D;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.math.Matrix4f;

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

    protected void initBuffers(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        CameraModel cameraModel = (CameraModel) model;

        IntBuffer ubo = Buffers.newDirectIntBuffer(1);
        gl.glGenBuffers(1, ubo);
        // float[] projMat = { //
        //         1f, 0f, 0f, 0f, //
        //         0f, 1f, 0f, 0f, //
        //         0f, 0f, 1f, 0f, //
        //         0f, 0f, 0f, 1f, //
        // }; 
        Matrix4f tempMat = new Matrix4f();
        Matrix4f projMat = new Matrix4f();
        Matrix4f viewMat = new Matrix4f().translate((float) cameraModel.x, (float) cameraModel.y, 0, tempMat);

        // float[] viewMat = { //
        //         1f, 0f, 0f, 0f, //
        //         0f, 1f, 0f, 0f, //
        //         0f, 0f, 1f, 0f, //
        //         (float) cameraModel.x, (float) cameraModel.y, 0f, 1f, //
        // };

        // FloatBuffer cameraMatBuffer = FloatBuffer.allocate(4 * 4 * 2).put(projMat).put(viewMat).flip();
        FloatBuffer cameraMatBuffer = FloatBuffer.allocate(4 * 4 * 2)
                .put(projMat.get(FloatBuffer.allocate(4 * 4)).flip())
                .put(viewMat.get(FloatBuffer.allocate(4 * 4)).flip()).flip();

        gl.glBindBuffer(GL4.GL_UNIFORM_BUFFER, ubo.get(0));
        gl.glBufferData(GL4.GL_UNIFORM_BUFFER, Float.BYTES * cameraMatBuffer.limit(), cameraMatBuffer,
                GL4.GL_STATIC_DRAW);

        cameraModel.setUBO(ubo.get(0));
    }

    @Override
    public void display(GLAutoDrawable drawable) {
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
