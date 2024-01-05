package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLDebugListener;
import com.jogamp.opengl.GLDebugMessage;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import medipro.config.InGameConfig;

public class HelloGraphics2D implements GLEventListener, GLDebugListener {
    protected static final Logger logger = Logger.getLogger("medipro");

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
        gl.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(0));
        gl.glBindSampler(sample2dLocation, samplerName.get(0));

        gl.glDrawArrays(GL4.GL_TRIANGLE_FAN, 0, 4);
    }

    private BufferedImage drawSomething() {
        BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.RED);
        g.fillOval(0, 0, 300, 300);
        g.dispose();
        return image;
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    int shaderProgram = -1;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();

        drawable.getContext().addGLDebugListener(this);
        // gl.glDebugMessageControl(GL4.GL_DONT_CARE, GL4.GL_DONT_CARE, GL4.GL_DONT_CARE, 0, null, false);
        // gl.glDebugMessageControl(GL4.GL_DONT_CARE, GL4.GL_DONT_CARE, GL4.GL_DEBUG_SEVERITY_HIGH, 0, null, true);
        // gl.glDebugMessageControl(GL4.GL_DONT_CARE, GL4.GL_DONT_CARE, GL4.GL_DEBUG_SEVERITY_MEDIUM, 0, null, true);

        String version = gl.glGetString(GL4.GL_VERSION);
        logger.info("OpenGLのバージョン: " + version);

        shaderProgram = initShaders(drawable);
        gl.glUseProgram(shaderProgram);

        initBuffers(drawable, shaderProgram);
        initTextures(drawable);
        initSamplers(drawable);
    }

    IntBuffer textureName = Buffers.newDirectIntBuffer(1);

    void initTextures(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        // try {
        // InputStream textureStream = new FileInputStream("img\\background\\Brickwall3_Texture.png");

        // TextureData textureData = TextureIO.newTextureData(gl.getGLProfile(), textureStream, false, TextureIO.PNG);
        TextureData textureData = AWTTextureIO.newTextureData(gl.getGLProfile(), drawSomething(), false);

        gl.glGenTextures(1, textureName);
        gl.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(0));

        gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_BASE_LEVEL, 0);
        gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAX_LEVEL, 2);

        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, textureData.getInternalFormat(), textureData.getWidth(),
                textureData.getHeight(), 0, textureData.getPixelFormat(), textureData.getPixelType(),
                textureData.getBuffer());

        gl.glGenerateMipmap(GL.GL_TEXTURE_2D);

        // } catch (IOException ex) {
        // logger.log(Level.SEVERE, null, ex);
        // }
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
    IntBuffer ubo = Buffers.newDirectIntBuffer(1);

    void initBuffers(GLAutoDrawable drawable, int program) {

        GL4 gl = drawable.getGL().getGL4();
        gl.glUseProgram(program);

        gl.glGenBuffers(2, vbo);
        gl.glGenBuffers(1, ubo);

        float[] vertices = { -0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f };
        float[] uv = { 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f };

        float[] projMat = { //
                1f, 0f, 0f, 0f, //
                0f, 1f, 0f, 0f, //
                0f, 0f, 1f, 0f, //
                0f, 0f, 0f, 1f, //
        };

        float[] viewMat = { //
                1f, 0f, 0f, 0f, //
                0f, 1f, 0f, 0f, //
                0f, 0f, 1f, 0f, //
                0f, 0f, 0f, 1f, //
        };

        FloatBuffer vertexBuffer = Buffers.newDirectFloatBuffer(vertices);
        FloatBuffer uvBuffer = Buffers.newDirectFloatBuffer(uv);

        FloatBuffer cameraMatBuffer = FloatBuffer.allocate(projMat.length + viewMat.length).put(projMat).put(viewMat)
                .flip();

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(0));
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, Float.BYTES * vertices.length, vertexBuffer, GL4.GL_STATIC_DRAW);

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(1));
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, Float.BYTES * uv.length, uvBuffer, GL4.GL_STATIC_DRAW);

        gl.glBindBuffer(GL4.GL_UNIFORM_BUFFER, ubo.get(0));
        gl.glBufferData(GL4.GL_UNIFORM_BUFFER, Float.BYTES * cameraMatBuffer.limit(), cameraMatBuffer,
                GL4.GL_STATIC_DRAW);
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
        int bindingPoint = 0; // 衝突してはいけない
        gl.glBindBuffer(GL4.GL_UNIFORM_BUFFER, ubo.get(0));
        gl.glUniformBlockBinding(program, cameraTransformLocation, bindingPoint);
        gl.glBindBufferBase(GL4.GL_UNIFORM_BUFFER, bindingPoint, ubo.get(0));
    }

    private void handleShaderCompileError(GLAutoDrawable drawable, int shader) {
        GL4 gl = drawable.getGL().getGL4();
        int[] success = new int[1];
        gl.glGetShaderiv(shader, GL4.GL_COMPILE_STATUS, success, 0);
        if (success[0] == GL4.GL_FALSE) {
            byte[] log = new byte[512];
            gl.glGetShaderInfoLog(shader, 512, null, 0, log, 0);
            System.out.println("Shader compilation failed: " + new String(log));
        }
    }

    // final String shaderFile = "shader/simple2d/simple2d";
    final String shaderFile = "shader/test/GameObject";

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
        GL4 gl = drawable.getGL().getGL4();
        gl.glViewport(0, 0, w, h);
        // initTextures(drawable);
        {
            // TextureData textureData = AWTTextureIO.newTextureData(gl.getGLProfile(), drawSomething(), false);

            // gl.glGenTextures(1, textureName);
            // gl.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(0));

            // gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_BASE_LEVEL, 0);
            // gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAX_LEVEL, 2);

            // gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, textureData.getInternalFormat(), textureData.getWidth(),
            //         textureData.getHeight(), 0, textureData.getPixelFormat(), textureData.getPixelType(),
            //         textureData.getBuffer());

            // gl.glGenerateMipmap(GL.GL_TEXTURE_2D);
        }

        // initSamplers(drawable);
        {
            // gl.glGenSamplers(1, samplerName);

            // gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_NEAREST);
            // gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_NEAREST);

            // gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_WRAP_S, GL4.GL_CLAMP_TO_EDGE);
            // gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_WRAP_T, GL4.GL_CLAMP_TO_EDGE);
        }
    }

    public static void main(String[] args) {

        final GLProfile profile = GLProfile.get(GLProfile.GL4);

        GLCapabilities capabilities = new GLCapabilities(profile);
        GLJPanel gljpanel = new GLJPanel(capabilities);

        HelloGraphics2D hello = new HelloGraphics2D();
        gljpanel.addGLEventListener(hello);
        gljpanel.setPreferredSize(new Dimension(640, 480));

        final JFrame frame = new JFrame("Hello, World!");
        frame.getContentPane().add(gljpanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            Boolean isIdle = true;

            @Override
            public void run() {
                if (isIdle) {
                    isIdle = false;
                    frame.repaint();
                    isIdle = true;
                } else {
                    logger.warning("Game is running slow");
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, (long) (1000f / InGameConfig.FPS));
    }

    @Override
    public void messageSent(GLDebugMessage arg0) {
        logger.info(arg0.getDbgMsg());
    }
}
