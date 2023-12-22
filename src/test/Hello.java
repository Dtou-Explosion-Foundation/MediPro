// https://github.com/cx20/hello/blob/d0e3a19ff57f675ec9f5b1c0ef8eee88321dc123/java/opengl4.6_jogl/triangle/Hello.java#L4
package test;

import java.nio.FloatBuffer;

import javax.swing.JFrame;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLDebugListener;
import com.jogamp.opengl.GLDebugMessage;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

public class Hello implements GLEventListener {

    int program = -1;

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

    private void initShader(GL4 gl) {
        String vertexSource = ""//
                + "#version 400 core                            \n" //
                + "layout(location = 0) in  vec3 position;      \n" //
                + "layout(location = 1) in  vec3 color;         \n" //
                + "out vec4 vColor;                             \n" //
                + "void main()                                  \n" //
                + "{                                            \n" //
                + "  vColor = vec4(color, 1.0);                 \n" //
                + "  gl_Position = vec4(position, 1.0);         \n" //
                + "}                                            \n";
        String fragmentSource = "" //
                + "#version 400 core                            \n" //
                + "precision mediump float;                     \n" //
                + "in  vec4 vColor;                             \n" //
                // + "in int time; \n" //
                + "out vec4 outColor;                           \n" //
                + "void main()                                  \n" //
                + "{                                            \n" //
                + "  outColor = vColor;                         \n" //
                + "}                                            \n";

        int vs = gl.glCreateShader(GL4.GL_VERTEX_SHADER);
        int fs = gl.glCreateShader(GL4.GL_FRAGMENT_SHADER);

        String[] vsrc = new String[] { vertexSource };
        int[] vlengths = new int[] { vsrc[0].length() };
        gl.glShaderSource(vs, 1, vsrc, vlengths, 0);
        gl.glCompileShader(vs);

        String[] fsrc = new String[] { fragmentSource };
        int[] flengths = new int[] { fsrc[0].length() };
        gl.glShaderSource(fs, 1, fsrc, flengths, 0);
        gl.glCompileShader(fs);

        int[] success = new int[1];
        gl.glGetShaderiv(vs, GL4.GL_COMPILE_STATUS, success, 0);
        if (success[0] == GL4.GL_FALSE) {
            byte[] log = new byte[512];
            gl.glGetShaderInfoLog(vs, 512, null, 0, log, 0);
            System.out.println("Vertex shader compilation failed: " + new String(log));
        }

        gl.glGetShaderiv(fs, GL4.GL_COMPILE_STATUS, success, 0);
        if (success[0] == GL4.GL_FALSE) {
            byte[] log = new byte[512];
            gl.glGetShaderInfoLog(fs, 512, null, 0, log, 0);
            System.out.println("Fragment shader compilation failed: " + new String(log));
        }

        program = gl.glCreateProgram();
        gl.glAttachShader(program, vs);
        gl.glAttachShader(program, fs);
        gl.glLinkProgram(program);
        gl.glUseProgram(program);

        // プログラムのリンク時のエラーを確認
        gl.glGetProgramiv(program, GL4.GL_LINK_STATUS, success, 0);
        if (success[0] == GL4.GL_FALSE) {
            byte[] log = new byte[512];
            gl.glGetProgramInfoLog(program, 512, null, 0, log, 0);
            System.out.println("Program linking failed: " + new String(log));
        }
    }

    int[] vbo = new int[2];

    private void initVertexArray(GL4 gl) {

        int posAttrib = gl.glGetAttribLocation(program, "position");
        gl.glEnableVertexAttribArray(posAttrib);

        int colAttrib = gl.glGetAttribLocation(program, "color");
        gl.glEnableVertexAttribArray(colAttrib);

        gl.glGenBuffers(2, vbo, 0);

        float[] colors = { 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f };
        float[] vertices = { 0.0f, 0.5f, 0.0f, 0.5f, -0.5f, 0.0f, -0.5f, -0.5f, 0.0f };

        FloatBuffer vertexBuffer = Buffers.newDirectFloatBuffer(vertices);
        FloatBuffer vertexColorBuffer = Buffers.newDirectFloatBuffer(colors);

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo[0]);
        gl.glVertexAttribPointer(posAttrib, 3, GL4.GL_FLOAT, false, 0, 0);

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo[1]);
        gl.glVertexAttribPointer(colAttrib, 3, GL4.GL_FLOAT, false, 0, 0);

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo[0]);
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, 4 * vertices.length, vertexBuffer, GL4.GL_STATIC_DRAW);

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo[1]);
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, 4 * colors.length, vertexColorBuffer, GL4.GL_STATIC_DRAW);

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glClear(GL4.GL_COLOR_BUFFER_BIT);
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
        final GLCanvas glcanvas = new GLCanvas(capabilities);

        Hello hello = new Hello();
        glcanvas.addGLEventListener(hello);
        glcanvas.setSize(640, 480);

        final JFrame frame = new JFrame("Hello, World!");
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
