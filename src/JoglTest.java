import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;

/**
 * A minimal program that draws with JOGL in a Swing JFrame using the AWT
 * GLJPanel.
 *
 * @author Wade Walker
 */
public class JoglTest {

    public static void main(String[] args) {
        // GLProfile glprofile = GLProfile.getDefault();
        GLProfile glprofile = GLProfile.get(GLProfile.GL4);
        GLCapabilities glcapabilities = new GLCapabilities(glprofile);
        GLJPanel gljpanel = new GLJPanel(glcapabilities);

        gljpanel.addGLEventListener(new GLEventListener() {
            int programId;

            @Override
            public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height) {
                // OneTriangle.setup(glautodrawable.getGL().getGL4(), width, height);
                GL4 var0 = glautodrawable.getGL().getGL4();
                int var1 = width;
                int var2 = height;
                // var0.glMatrixMode(5889);
                // var0.glLoadIdentity();
                // GLUGL4es1 var3 = new GLUGL4es1();
                // var3.gluOrtho2D(0.0F, (float) var1, 0.0F, (float) var2);
                // var0.glMatrixMode(5888);
                // var0.glLoadIdentity();
                var0.glViewport(0, 0, var1, var2);
            }

            @Override
            public void init(GLAutoDrawable glautodrawable) {
                GL4 gl = glautodrawable.getGL().getGL4();
                programId = compileShaders(gl);
            }

            @Override
            public void dispose(GLAutoDrawable glautodrawable) {
            }

            float points[] = { 0.0f, 0.5f, 0.0f, 0.5f, -0.5f, 0.0f, -0.5f, -0.5f, 0.0f, };
            FloatBuffer bufPos = Buffers.newDirectFloatBuffer(points);
            int vertexbuffer[] = new int[1];

            @Override
            public void display(GLAutoDrawable glautodrawable) {
                GL4 gl = glautodrawable.getGL().getGL4();
                int width = glautodrawable.getSurfaceWidth();
                int height = glautodrawable.getSurfaceHeight();

                gl.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);
                gl.glUseProgram(programId);

                gl.glGenBuffers(1, vertexbuffer, 0);
                gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vertexbuffer[0]);
                // gl.glBufferData(GL4.GL_ARRAY_BUFFER, bufPos.capacity(), bufPos,
                // GL.GL_STATIC_DRAW);
                gl.glBufferData(GL4.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * 3 * 3, bufPos, GL4.GL_STATIC_DRAW);

                gl.glEnableVertexAttribArray(0);
                gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vertexbuffer[0]);

                gl.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, 0, 0);

                gl.glDrawArrays(GL4.GL_TRIANGLES, 0, 3);
                gl.glBeginConditionalRender(width, height);
            }
        });

        final JFrame jframe = new JFrame("One Triangle Swing GLJPanel");
        jframe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowevent) {
                jframe.dispose();
                System.exit(0);
            }
        });

        jframe.getContentPane().add(gljpanel, BorderLayout.CENTER);
        jframe.setSize(640, 480);
        jframe.setVisible(true);
    }

    static int compileShaders(GL4 gl) {
        int vert = gl.glCreateShader(GL4.GL_VERTEX_SHADER);
        int frag = gl.glCreateShader(GL4.GL_FRAGMENT_SHADER);

        try (BufferedReader brv = new BufferedReader(new FileReader("shader/default.vert"))) {
            String vertSrc = brv.lines().collect(Collectors.joining("\n"));
            System.out.println("******** vertSrc ********\n" + vertSrc + "\n*************************\n");
            gl.glShaderSource(vert, 1, new String[] { vertSrc }, null);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try (BufferedReader brf = new BufferedReader(new FileReader("shader/default.frag"))) {
            String fragSrc = brf.lines().collect(Collectors.joining("\n"));
            System.out.println("******** fragSrc ********\n" + fragSrc + "\n*************************\n");
            gl.glShaderSource(frag, 1, new String[] { fragSrc }, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gl.glCompileShader(frag);

        int shaderprogram = gl.glCreateProgram();
        gl.glAttachShader(shaderprogram, vert);
        gl.glAttachShader(shaderprogram, frag);
        gl.glLinkProgram(shaderprogram);
        gl.glValidateProgram(shaderprogram);

        // gl.glUseProgram(shaderprogram);
        return shaderprogram;
    }
}