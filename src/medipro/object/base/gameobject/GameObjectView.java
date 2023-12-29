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

    @Override
    public void display(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glUseProgram(shaderProgram);

        this.bindBuffers(drawable, shaderProgram);

        int modelMatUniform = gl.glGetUniformLocation(shaderProgram, "modelMat");

        float cameraScale = this.model.world.camera.isPresent() ? (float) this.model.world.camera.get().scale : 1f;

        Matrix4f tempMat = new Matrix4f();
        Matrix4f modelMat = new Matrix4f() // モデルの座標変換行列
                .translate((float) model.x, (float) model.y, 0, tempMat) // 座標
                .scale(InGameConfig.WINDOW_WIDTH, InGameConfig.WINDOW_HEIGHT, 1, tempMat)// スケーリング
                .scale(1f / cameraScale, 1f / cameraScale, 1, tempMat)// スケーリング
        ;

        FloatBuffer modelMatBuffer = modelMat.transpose().get(FloatBuffer.allocate(4 * 4)).flip();
        gl.glUniformMatrix4fv(modelMatUniform, 1, true, modelMatBuffer);

        int sample2dLocation = gl.glGetUniformLocation(shaderProgram, "uTexture");
        gl.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(0));
        // gl.glBindTextureUnit(sample2dLocation, textureName.get(0));
        gl.glBindSampler(sample2dLocation, samplerName.get(0));

        gl.glDrawArrays(GL4.GL_TRIANGLE_FAN, 0, 4);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    int shaderProgram = -1;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();

        shaderProgram = initShaders(drawable);
        gl.glUseProgram(shaderProgram);

        initBuffers(drawable, shaderProgram);
        initTextures(drawable);
        initSamplers(drawable);
    }

    IntBuffer textureName = Buffers.newDirectIntBuffer(1);

    void initTextures(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        // InputStream textureStream;
        // try {
        //  textureStream = new FileInputStream("img\\background\\Brickwall3_Texture.png");
        // } catch (IOException ex) {
        //     logger.log(Level.SEVERE, ex.toString());
        // }
        // TextureData textureData = TextureIO.newTextureData(gl.getGLProfile(), textureStream, false, TextureIO.PNG);
        double cameraScale = this.model.world.camera.isPresent() ? this.model.world.camera.get().scale : 1;

        BufferedImage bufferedImage = new BufferedImage((int) (InGameConfig.WINDOW_WIDTH / cameraScale),
                (int) (InGameConfig.WINDOW_HEIGHT / cameraScale), BufferedImage.TYPE_INT_ARGB);
        {
            Graphics2D g = bufferedImage.createGraphics();
            AffineTransform transform = new AffineTransform();
            transform.translate(InGameConfig.WINDOW_WIDTH / cameraScale / 2,
                    InGameConfig.WINDOW_HEIGHT / cameraScale / 2);
            g.setTransform(transform);
            draw(g);
            // draw(g, null);
            g.dispose();
        }
        TextureData textureData = AWTTextureIO.newTextureData(gl.getGLProfile(), bufferedImage, false);

        gl.glGenTextures(1, textureName);
        gl.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(0));

        gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_BASE_LEVEL, 0);
        gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAX_LEVEL, 2);

        gl.glTexImage2D(GL4.GL_TEXTURE_2D, 0, textureData.getInternalFormat(), textureData.getWidth(),
                textureData.getHeight(), 0, textureData.getPixelFormat(), textureData.getPixelType(),
                textureData.getBuffer());

        gl.glGenerateMipmap(GL4.GL_TEXTURE_2D);

    }

    IntBuffer samplerName = Buffers.newDirectIntBuffer(1);

    void initSamplers(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glGenSamplers(1, samplerName);

        gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_NEAREST);
        gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_NEAREST);

        gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_WRAP_S, GL4.GL_CLAMP_TO_EDGE);
        gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_WRAP_T, GL4.GL_CLAMP_TO_EDGE);
    }

    IntBuffer vbo = Buffers.newDirectIntBuffer(2);

    protected void initBuffers(GLAutoDrawable drawable, int program) {

        GL4 gl = drawable.getGL().getGL4();
        gl.glUseProgram(program);

        gl.glGenBuffers(2, vbo);

        float[] vertices = { -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f };
        float[] uv = { 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f };

        FloatBuffer vertexBuffer = Buffers.newDirectFloatBuffer(vertices);
        FloatBuffer uvBuffer = Buffers.newDirectFloatBuffer(uv);

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(0));
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, Float.BYTES * vertices.length, vertexBuffer, GL4.GL_STATIC_DRAW);

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(1));
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, Float.BYTES * uv.length, uvBuffer, GL4.GL_STATIC_DRAW);

    }

    void bindBuffers(GLAutoDrawable drawable, int program) {
        GL4 gl = drawable.getGL().getGL4();

        int vertexAttrib = gl.glGetAttribLocation(program, "aPosition");
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(0));
        gl.glEnableVertexAttribArray(vertexAttrib);
        gl.glVertexAttribPointer(vertexAttrib, 2, GL4.GL_FLOAT, false, 0, 0);

        int uvAttrib = gl.glGetAttribLocation(program, "aTexcoord");
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(1));
        gl.glEnableVertexAttribArray(uvAttrib);
        gl.glVertexAttribPointer(uvAttrib, 2, GL4.GL_FLOAT, false, 0, 0);

        int cameraTransformLocation = gl.glGetUniformBlockIndex(program, "CameraTransform");
        int bindingPoint = nextBindingPoint++; // 衝突してはいけない
        int cameraUbo = this.model.world.camera.isPresent() ? this.model.world.camera.get().getUBO() : -1;
        gl.glBindBuffer(GL4.GL_UNIFORM_BUFFER, cameraUbo);
        gl.glUniformBlockBinding(program, cameraTransformLocation, bindingPoint);
        gl.glBindBufferBase(GL4.GL_UNIFORM_BUFFER, bindingPoint, cameraUbo);
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

    // final String shaderFile = "shader/simple2d/simple2d";
    final String shaderFile = "shader/gameobject/GameObject";

    int initShaders(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        int program = gl.glCreateProgram();
        try (BufferedReader brv = new BufferedReader(new FileReader(shaderFile + ".vert"))) {
            int vert = gl.glCreateShader(GL4.GL_VERTEX_SHADER);
            String vertSrc = brv.lines().collect(Collectors.joining("\n"));
            gl.glShaderSource(vert, 1, new String[] { vertSrc }, null);
            gl.glCompileShader(vert);
            this.handleShaderCompileError(drawable, vert);
            gl.glAttachShader(program, vert);
        } catch (IOException e) {
            logger.warning(e.toString());
        }

        try (BufferedReader brv = new BufferedReader(new FileReader(shaderFile + ".frag"))) {
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

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {

    }

}
