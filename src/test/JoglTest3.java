package test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;

public class JoglTest3 implements GLEventListener { // (1)

    // Animator animator;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JoglTest3();
            }
        });
    }

    public JoglTest3() {
        GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL4)); // (2)
        JFrame frame = new JFrame(); // (3)
        frame.setTitle("First demo (Swing)"); // (4)

        frame.addWindowListener(new WindowAdapter() { // (6)
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // GLCanvas canvas = new GLCanvas(caps);
        // canvas.setPreferredSize(new Dimension(300, 300)); // (5)
        // canvas.addGLEventListener(this); // (7)

        GLJPanel panel = new GLJPanel(caps);
        panel.setPreferredSize(new Dimension(300, 300)); // (5)
        panel.addGLEventListener(this); // (7)

        // animator = new Animator(); // (8)
        // animator.add(canvas);
        // animator.start();

        frame.add(panel, BorderLayout.CENTER);
        frame.setLocation(300, 300); // (9)
        frame.pack();
        frame.setVisible(true); // (10)
    }

    int[] vao;
    int[] vbo;

    int shaderProgram;

    float[] vertices = { -0.9f, -0.9f, 0.9f, -0.9f, 0.9f, 0.9f, -0.9f, 0.9f };
    int[] indices = { 0, 1, 2, 2, 1, 0 };

    @Override
    public void init(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);

        // 頂点データの準備

        // VAO(VAOの作成はGL4から導入された機能です)
        vao = new int[1];
        gl.glGenVertexArrays(1, vao, 0);
        gl.glBindVertexArray(vao[0]);

        // VBO（頂点バッファオブジェクト）の作成とデータの設定
        vbo = new int[2];
        gl.glGenBuffers(2, vbo, 0);
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo[0]);
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, vertices.length * Float.BYTES, FloatBuffer.wrap(vertices),
                GL4.GL_STATIC_DRAW);
        int positionAttrib = 0;
        gl.glEnableVertexAttribArray(positionAttrib);
        gl.glVertexAttribPointer(positionAttrib, 2, GL4.GL_FLOAT, false, 2 * Float.BYTES, 0);

        gl.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, vbo[1]);
        gl.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, indices.length * Integer.BYTES, IntBuffer.wrap(indices),
                GL4.GL_STATIC_DRAW);

        // Shaderを用意
        shaderProgram = compileShaders(gl);

        // // 頂点属性の有効化と設定
        // int positionAttrib = gl.glGetUniformLocation(shaderProgram, "aPosition");
        // System.err.println("positionAttrib = " + positionAttrib);
        // gl.glEnableVertexAttribArray(positionAttrib);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glBindVertexArray(vao[0]);
        // gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo[0]);

        gl.glDrawArrays(GL4.GL_LINE_LOOP, 0, 4);
        gl.glDrawElements(GL4.GL_TRIANGLES, indices.length, GL4.GL_UNSIGNED_INT, 0);

        // gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
        gl.glBindVertexArray(0);
        gl.glUseProgram(0);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // if (animator != null)
        // animator.stop();
    }

    private void initDebug(GL4 gl4) {

        this.addGLDebugListener(new GlDebugOutput());
        // Turn off all the debug
        gl4.glDebugMessageControl(GL_DONT_CARE, // source
                GL_DONT_CARE, // type
                GL_DONT_CARE, // severity
                0, // count
                null, // id
                false); // enabled
        // Turn on all OpenGL Errors, shader compilation/linking errors, or
        // highly-dangerous undefined behavior
        gl4.glDebugMessageControl(GL_DONT_CARE, // source
                GL_DONT_CARE, // type
                GL_DEBUG_SEVERITY_HIGH, // severity
                0, // count
                null, // id
                true); // enabled
        // Turn on all major performance warnings, shader compilation/linking warnings
        // or the use of deprecated functions
        gl4.glDebugMessageControl(GL_DONT_CARE, // source
                GL_DONT_CARE, // type
                GL_DEBUG_SEVERITY_MEDIUM, // severity
                0, // count
                null, // id
                true); // enabled
    }

    static int compileShaders(GL4 gl) {
        int vert = gl.glCreateShader(GL4.GL_VERTEX_SHADER);
        int frag = gl.glCreateShader(GL4.GL_FRAGMENT_SHADER);

        try (BufferedReader brv = new BufferedReader(new FileReader("shader/default.vert"))) {
            String vertSrc = brv.lines().collect(Collectors.joining("\n"));
            System.out.println("******** vertSrc ********\n" + vertSrc + "\n*************************\n");
            gl.glShaderSource(vert, 1, new String[] { vertSrc }, null);
            gl.glCompileShader(vert);
            gl.glGetShaderInfoLog(frag, GL4.GL_COMPILE_STATUS, null, vert, null, frag);
            if (gl.glGetError() != GL.GL_NO_ERROR)
                System.err.println("[error] Vertex shader compilation failed.");
        } catch (IOException e1) {
            System.err.println("error on compile\n");
            e1.printStackTrace();
        }
        try (BufferedReader brf = new BufferedReader(new FileReader("shader/default.frag"))) {
            String fragSrc = brf.lines().collect(Collectors.joining("\n"));
            System.out.println("******** fragSrc ********\n" + fragSrc + "\n*************************\n");
            gl.glShaderSource(frag, 1, new String[] { fragSrc }, null);
            gl.glCompileShader(frag);
            if (gl.glGetError() != GL.GL_NO_ERROR)
                System.err.println("[error] Fragment shader compilation failed.");
        } catch (IOException e) {
            System.err.println("error on compile\n");
            e.printStackTrace();
        }

        int shaderprogram = gl.glCreateProgram();
        gl.glAttachShader(shaderprogram, vert);
        gl.glAttachShader(shaderprogram, frag);
        gl.glLinkProgram(shaderprogram);
        if (gl.glGetError() != GL.GL_NO_ERROR)
            System.err.println("[error] Program linking failed.");
        // gl.glValidateProgram(shaderprogram);

        // int positionAttrib = gl.glGetAttribLocation(shaderprogram, "aPosition");
        // System.err.println("shaderprogram positionAttrib = " + positionAttrib);

        // gl.glUseProgram(shaderprogram);
        return shaderprogram;
    }
}