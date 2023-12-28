// https://github.com/cx20/hello/blob/d0e3a19ff57f675ec9f5b1c0ef8eee88321dc123/java/opengl4.6_jogl/triangle/Hello.java#L4
package test;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLDebugListener;
import com.jogamp.opengl.GLDebugMessage;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;

import medipro.config.InGameConfig;

public class HelloCustom implements GLEventListener {

    int program = -1;

    String shaderName = "test";

    @Override
    public void init(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        initDebug(drawable);
        initBuffers(gl);
        initShader(gl);
        initVertexArray(gl);
    }

    private void initDebug(GLAutoDrawable drawable) {
        drawable.getContext().addGLDebugListener(new GLDebugListener() {
            @Override
            public void messageSent(GLDebugMessage event) {
                System.out.println(event);
            }
        });
    }

    private void initBuffers(GL4 gl) {

    }

    private void printAttributeList(GL4 gl, int shaderProgram) {
        // プログラムに関連付けられた属性の数を取得
        int[] attribCount = new int[1];
        gl.glGetProgramiv(shaderProgram, GL4.GL_ACTIVE_ATTRIBUTES, attribCount, 0);

        // 各属性の情報を取得
        for (int i = 0; i < attribCount[0]; i++) {
            int[] size = new int[1];
            int[] type = new int[1];
            byte[] name = new byte[256]; // 属性名の最大長を設定

            // 属性の情報を取得
            gl.glGetActiveAttrib(shaderProgram, i, name.length, null, 0, size, 0, type, 0, name, 0);

            // 属性名を文字列に変換して表示
            String attributeName = new String(name).trim();
            System.out.println("Attribute " + i + ": " + attributeName);
        }
    }

    private void initShader(GL4 gl) {
        program = gl.glCreateProgram();

        try (BufferedReader brv = new BufferedReader(
                new FileReader("shader/" + shaderName + "/" + shaderName + ".vert"))) {
            int vs = gl.glCreateShader(GL4.GL_VERTEX_SHADER);
            String vertexSource = brv.lines().collect(Collectors.joining("\n"));
            System.err.println("******** vertSrc ********\n" + vertexSource + "\n*************************\n");
            gl.glShaderSource(vs, 1, new String[] { vertexSource }, new int[] { vertexSource.length() }, 0);
            gl.glCompileShader(vs);
            gl.glAttachShader(program, vs);
        } catch (IOException e) {
            System.err.println(e.toString());
        }

        try (BufferedReader brv = new BufferedReader(
                new FileReader("shader/" + shaderName + "/" + shaderName + ".frag"))) {
            int frag = gl.glCreateShader(GL4.GL_FRAGMENT_SHADER);
            String fragSrc = brv.lines().collect(Collectors.joining("\n"));
            System.err.println("******** fragSrc ********\n" + fragSrc + "\n*************************\n");
            gl.glShaderSource(frag, 1, new String[] { fragSrc }, new int[] { fragSrc.length() }, 0);
            gl.glCompileShader(frag);
            gl.glAttachShader(program, frag);
        } catch (IOException e) {
            System.err.println(e.toString());
        }
        gl.glLinkProgram(program);
        gl.glUseProgram(program);
        printAttributeList(gl, program);
    }

    int[] vbo = new int[2];
    int posAttrib = -1;
    int colAttrib = -1;

    private void initVertexArray(GL4 gl) {

        posAttrib = gl.glGetAttribLocation(program, "position");
        gl.glEnableVertexAttribArray(posAttrib);

        colAttrib = gl.glGetAttribLocation(program, "color");
        gl.glEnableVertexAttribArray(colAttrib);

        gl.glGenBuffers(2, vbo, 0);

        float[] colors = { 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f };
        float[] vertices = { 0.0f, 0.5f, 0.0f, 0.5f, -0.5f, 0.0f, -0.5f, -0.5f, 0.0f };

        FloatBuffer vertexBuffer = Buffers.newDirectFloatBuffer(vertices);
        FloatBuffer vertexColorBuffer = Buffers.newDirectFloatBuffer(colors);

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo[0]);
        gl.glVertexAttribPointer(posAttrib, 3, GL4.GL_FLOAT, false, 0, 0);
        // gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo[0]);
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, 4 * vertices.length, vertexBuffer, GL4.GL_STATIC_DRAW);

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo[1]);
        gl.glVertexAttribPointer(colAttrib, 3, GL4.GL_FLOAT, false, 0, 0);
        // gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo[1]);
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, 4 * colors.length, vertexColorBuffer, GL4.GL_STATIC_DRAW);

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glUseProgram(program);
        gl.glClear(GL4.GL_COLOR_BUFFER_BIT);

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo[0]);
        gl.glEnableVertexAttribArray(posAttrib);
        gl.glVertexAttribPointer(posAttrib, 3, GL4.GL_FLOAT, false, 0, 0);

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo[1]);
        gl.glEnableVertexAttribArray(colAttrib);
        gl.glVertexAttribPointer(colAttrib, 3, GL4.GL_FLOAT, false, 0, 0);

        int modelMatUniform = gl.glGetUniformLocation(program, "modelMat");
        float[] modelMat = { //
                1f, 0f, 0f, 0.5f, //
                0f, 1f, 0f, 0.5f, //
                0f, 0f, 1f, 0f, //
                0f, 0f, 0f, 1f, //
        };
        FloatBuffer modelMatBuffer = FloatBuffer.wrap(modelMat);
        gl.glUniformMatrix4fv(modelMatUniform, 1, true, modelMatBuffer);

        gl.glDrawArrays(GL4.GL_TRIANGLES, 0, 3);
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
    }

    @Override
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
    }

    public static void main(String[] args) {

        final GLProfile profile = GLProfile.get(GLProfile.GL4);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // final GLCanvas glcanvas = new GLCanvas(capabilities);
        GLJPanel gljpanel = new GLJPanel(capabilities);

        HelloCustom hello = new HelloCustom();
        // glcanvas.addGLEventListener(hello);
        // glcanvas.setSize(640, 480);
        gljpanel.addGLEventListener(hello);
        gljpanel.setPreferredSize(new Dimension(640, 480));

        final JFrame frame = new JFrame("Hello, World!");
        // frame.getContentPane().add(glcanvas);
        frame.getContentPane().add(gljpanel);
        frame.pack();
        // frame.setSize(frame.getContentPane().getPreferredSize());
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
                    // logger.warning("Game is running slow");
                    System.err.println("Game is running slow");
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, (long) (1000f / InGameConfig.FPS));
    }
}
