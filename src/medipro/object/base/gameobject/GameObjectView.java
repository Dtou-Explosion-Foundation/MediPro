package medipro.object.base.gameobject;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.math.Matrix4f;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import medipro.config.InGameConfig;

/**
 * ゲームオブジェクトのビュークラス。 複数のモデルを保持し、順に描画することができる。
 */
public abstract class GameObjectView implements GLEventListener {
    /**
     * ロガー.
     */
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * 格納しているモデル.
     */
    public GameObjectModel model;

    protected static int nextBindingPoint = 0;

    /**
     * ゲームオブジェクトビューを生成する。
     * 
     * @param model 格納するモデル
     */
    public GameObjectView(GameObjectModel model) {
        this.model = model;
    }

    /**
     * 格納しているモデルに対してそれぞれdraw()を呼び出す。
     * 
     * @param g               描画対象のGraphics2D
     * @param cameraTransform カメラ座標へ変換するためのアフィン変換行列
     */
    public void draw(Graphics2D g, AffineTransform cameraTransform) {
        g.setTransform(cameraTransform);
        g.transform(model.getTransformMatrix());
        this.draw(g);
    }

    /**
     * モデルを元に描画を行う
     * 
     * @param g 描画対象のGraphics2D
     */
    protected abstract void draw(Graphics2D g);

    protected int shaderProgram = -1;
    protected IntBuffer textureName;
    protected IntBuffer samplerName;
    protected IntBuffer vbo;

    protected String getShaderPath(String ext) {
        return "shader/G2dWrapper/G2dWrapper" + "." + ext;
    }

    protected float getSpriteWidth() {
        return InGameConfig.WINDOW_WIDTH;
    }

    protected float getSpriteHeight() {
        return InGameConfig.WINDOW_HEIGHT;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();

        this.initNames();

        shaderProgram = this.initShaders(drawable);
        gl.glUseProgram(shaderProgram);

        this.initBuffers(drawable, shaderProgram);
        this.initTextures(drawable);
        this.initSamplers(drawable);
    }

    protected void initNames() {
        textureName = Buffers.newDirectIntBuffer(1);
        samplerName = Buffers.newDirectIntBuffer(1);
        vbo = Buffers.newDirectIntBuffer(2);
    }

    protected void initTextures(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        AffineTransform cameraTransform = this.model.world.camera.isPresent()
                ? this.model.world.camera.get().getTransformMatrix()
                : new AffineTransform();

        BufferedImage bufferedImage = new BufferedImage(InGameConfig.WINDOW_WIDTH, InGameConfig.WINDOW_HEIGHT,
                BufferedImage.TYPE_INT_ARGB);
        {
            Graphics2D g = bufferedImage.createGraphics();
            draw(g, cameraTransform);
            g.dispose();
        }
        TextureData textureData = AWTTextureIO.newTextureData(gl.getGLProfile(), bufferedImage, false);

        gl.glGenTextures(1, textureName);
        gl.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(0));

        gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_BASE_LEVEL, 0);
        gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAX_LEVEL, 0);

        gl.glTexImage2D(GL4.GL_TEXTURE_2D, 0, textureData.getInternalFormat(), textureData.getWidth(),
                textureData.getHeight(), 0, textureData.getPixelFormat(), textureData.getPixelType(),
                textureData.getBuffer());
    }

    protected void initSamplers(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glGenSamplers(1, samplerName);

        gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_NEAREST);
        gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_NEAREST);

        gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_WRAP_S, GL4.GL_CLAMP_TO_EDGE);
        gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_WRAP_T, GL4.GL_CLAMP_TO_EDGE);
    }

    protected void initBuffers(GLAutoDrawable drawable, int program) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glUseProgram(program);

        gl.glGenBuffers(2, vbo);

        float[] vertices = { -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f };
        float[] uv = { 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f };

        FloatBuffer vertexBuffer = Buffers.newDirectFloatBuffer(vertices);
        FloatBuffer uvBuffer = Buffers.newDirectFloatBuffer(uv);

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(0));
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, Float.BYTES * vertices.length, vertexBuffer, GL4.GL_STATIC_DRAW);

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(1));
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, Float.BYTES * uv.length, uvBuffer, GL4.GL_STATIC_DRAW);
    }

    protected int initShaders(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        int program = gl.glCreateProgram();
        try (BufferedReader brv = new BufferedReader(new FileReader(getShaderPath("vert")))) {
            int vert = gl.glCreateShader(GL4.GL_VERTEX_SHADER);
            String vertSrc = brv.lines().collect(Collectors.joining("\n"));
            gl.glShaderSource(vert, 1, new String[] { vertSrc }, null);
            gl.glCompileShader(vert);
            this.handleShaderCompileError(drawable, vert);
            gl.glAttachShader(program, vert);
        } catch (IOException e) {
            logger.warning(e.toString());
        }

        try (BufferedReader brv = new BufferedReader(new FileReader(getShaderPath("frag")))) {
            int frag = gl.glCreateShader(GL4.GL_FRAGMENT_SHADER);
            String fragSrc = brv.lines().collect(Collectors.joining("\n"));
            gl.glShaderSource(frag, 1, new String[] { fragSrc }, null);
            gl.glCompileShader(frag);
            this.handleShaderCompileError(drawable, frag);
            gl.glAttachShader(program, frag);
        } catch (IOException e) {
            logger.warning(e.toString());
        }

        gl.glLinkProgram(program);
        // プログラムのリンク時のエラーを確認
        int[] success = new int[1];
        gl.glGetProgramiv(program, GL4.GL_LINK_STATUS, success, 0);
        if (success[0] == GL4.GL_FALSE) {
            byte[] log = new byte[512];
            gl.glGetProgramInfoLog(program, 512, null, 0, log, 0);
            System.out.println("Program linking failed: " + new String(log));
        }
        return program;
    }

    private void handleShaderCompileError(GLAutoDrawable drawable, int shader) {
        GL4 gl = drawable.getGL().getGL4();
        int[] success = new int[1];
        gl.glGetShaderiv(shader, GL4.GL_COMPILE_STATUS, success, 0);
        if (success[0] == GL4.GL_FALSE) {
            byte[] log = new byte[512];
            gl.glGetShaderInfoLog(shader, 512, null, 0, log, 0);
            System.out.println("Vertex shader compilation failed: " + new String(log));
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glUseProgram(shaderProgram);

        this.bindBuffers(drawable, shaderProgram);

        this.updateTextures(drawable);
        this.updateUniforms(drawable);

        gl.glDrawArrays(GL4.GL_TRIANGLE_FAN, 0, 4);
    }

    protected void bindBuffers(GLAutoDrawable drawable, int program) {
        GL4 gl = drawable.getGL().getGL4();

        int vertexLocation = gl.glGetAttribLocation(program, "aPosition");
        if (vertexLocation != -1) {
            gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(0));
            gl.glEnableVertexAttribArray(vertexLocation);
            gl.glVertexAttribPointer(vertexLocation, 2, GL4.GL_FLOAT, false, 0, 0);
        }

        int uvLocation = gl.glGetAttribLocation(program, "aTexcoord");
        if (uvLocation != -1) {
            gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(1));
            gl.glEnableVertexAttribArray(uvLocation);
            gl.glVertexAttribPointer(uvLocation, 2, GL4.GL_FLOAT, false, 0, 0);
        }

        int cameraTransformLocation = gl.glGetUniformBlockIndex(program, "CameraTransform");
        if (cameraTransformLocation != -1) {
            int bindingPoint = nextBindingPoint++; // 衝突してはいけない
            int cameraUbo = this.model.world.camera.isPresent() ? this.model.world.camera.get().getUBO() : -1;
            gl.glBindBuffer(GL4.GL_UNIFORM_BUFFER, cameraUbo);
            gl.glUniformBlockBinding(program, cameraTransformLocation, bindingPoint);
            gl.glBindBufferBase(GL4.GL_UNIFORM_BUFFER, bindingPoint, cameraUbo);
        }
    }

    protected void updateTextures(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        AffineTransform cameraTransform = this.model.world.camera.isPresent()
                ? this.model.world.camera.get().getTransformMatrix()
                : new AffineTransform();

        BufferedImage bufferedImage = new BufferedImage(InGameConfig.WINDOW_WIDTH, InGameConfig.WINDOW_HEIGHT,
                BufferedImage.TYPE_INT_ARGB);
        {
            Graphics2D g = bufferedImage.createGraphics();
            draw(g, cameraTransform);
            g.dispose();
        }
        TextureData textureData = AWTTextureIO.newTextureData(gl.getGLProfile(), bufferedImage, false);

        gl.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(0));
        gl.glTexSubImage2D(GL4.GL_TEXTURE_2D, 0, 0, 0, textureData.getWidth(), textureData.getHeight(),
                textureData.getPixelFormat(), textureData.getPixelType(), textureData.getBuffer());
    }

    protected void updateUniforms(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        int modelMatUniform = gl.glGetUniformLocation(shaderProgram, "modelMat");
        if (modelMatUniform != -1) {

            Matrix4f tempMat = new Matrix4f();
            Matrix4f modelMat = new Matrix4f() // モデルの座標変換行列
                    .translate((float) model.x, (float) model.y, 0, tempMat) // 座標
                    .scale(getSpriteWidth(), getSpriteHeight(), 1, tempMat)// スケーリング
            ;
            FloatBuffer modelMatBuffer = modelMat.transpose().get(FloatBuffer.allocate(4 * 4)).flip();
            gl.glUniformMatrix4fv(modelMatUniform, 1, true, modelMatBuffer);
        }
        int sample2dLocation = gl.glGetUniformLocation(shaderProgram, "uTexture");
        if (sample2dLocation != -1) {
            gl.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(0));
            gl.glBindSampler(sample2dLocation, samplerName.get(0));
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

}
