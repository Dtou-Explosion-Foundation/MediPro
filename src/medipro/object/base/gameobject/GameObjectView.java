package medipro.object.base.gameobject;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

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
    public ArrayList<GameObjectModel> models;

    public boolean drawAsUI = false;

    /**
     * ゲームオブジェクトビューを生成する。
     */
    public GameObjectView() {

        this.models = new ArrayList<GameObjectModel>();
    }

    /**
     * ゲームオブジェクトビューを生成する。
     * 
     * @param models 格納するモデル
     */
    public GameObjectView(GameObjectModel... models) {
        this();
        for (GameObjectModel model : models) {
            this.models.add(model);
        }
    }

    /**
     * 格納しているモデルに対してそれぞれdraw()を呼び出す。
     * 
     * @param g               描画対象のGraphics2D
     * @param cameraTransform カメラ座標へ変換するためのアフィン変換行列
     */
    public void drawModels(Graphics2D g, AffineTransform cameraTransform) {
        for (GameObjectModel model : models) {
            g.setTransform(cameraTransform);
            if (!drawAsUI || !InGameConfig.USE_OPENGL)
                g.transform(model.getTransformMatrix());
            this.draw(model, g);
        }
    }

    /**
     * モデルを元に描画を行う
     * 
     * @param model 描画対象のモデル
     * @param g     描画対象のGraphics2D
     */
    abstract public void draw(GameObjectModel model, Graphics2D g);

    @Override
    public void display(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glUseProgram(shaderProgram);

        this.bindBuffers(drawable, shaderProgram);

        int modelMatUniform = gl.glGetUniformLocation(shaderProgram, "modelMat");
        float[] modelMat = { //
                1f, 0f, 0f, 0.4f, //
                0f, 1f, 0f, 0f, //
                0f, 0f, 1f, 0f, //
                0f, 0f, 0f, 1f, //
        };
        FloatBuffer modelMatBuffer = FloatBuffer.wrap(modelMat);
        gl.glUniformMatrix4fv(modelMatUniform, 1, true, modelMatBuffer);

        int sample2dLocation = gl.glGetUniformLocation(shaderProgram, "uTexture");
        gl.glBindTextureUnit(sample2dLocation, textureName.get(0));
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

        int modelMatUniform = gl.glGetUniformLocation(shaderProgram, "modelMat");
        int uTextureUniform = gl.glGetUniformLocation(shaderProgram, "uTexture");
        logger.info("modelMatUniform = " + modelMatUniform);
        logger.info("uTextureUniform = " + uTextureUniform);

        initBuffers(drawable, shaderProgram);
        initTextures(drawable);
        initSamplers(drawable);
    }

    IntBuffer textureName = Buffers.newDirectIntBuffer(1);

    void initTextures(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        try {
            InputStream texture = new FileInputStream("img\\background\\Brickwall3_Texture.png");

            TextureData textureData = TextureIO.newTextureData(gl.getGLProfile(), texture, false, TextureIO.PNG);

            gl.glCreateTextures(GL4.GL_TEXTURE_2D, 1, textureName);

            gl.glTextureParameteri(textureName.get(0), GL4.GL_TEXTURE_BASE_LEVEL, 0);
            gl.glTextureParameteri(textureName.get(0), GL4.GL_TEXTURE_MAX_LEVEL, 0);

            gl.glTextureStorage2D(textureName.get(0), 1, // level
                    textureData.getInternalFormat(), textureData.getWidth(), textureData.getHeight());

            gl.glTextureSubImage2D(textureName.get(0), 0, // level
                    0, 0, // offset
                    textureData.getWidth(), textureData.getHeight(), textureData.getPixelFormat(),
                    textureData.getPixelType(), textureData.getBuffer());

        } catch (IOException ex) {
            // Logger.getLogger(HelloGlobe.class.getName()).log(Level.SEVERE, null, ex);
            logger.log(Level.SEVERE, null, ex);
        }
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
    IntBuffer ubo = Buffers.newDirectIntBuffer(2);

    void initBuffers(GLAutoDrawable drawable, int program) {

        GL4 gl = drawable.getGL().getGL4();
        gl.glUseProgram(program);

        gl.glGenBuffers(2, vbo);
        gl.glGenBuffers(2, ubo);

        float[] vertices = { -0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f };
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
