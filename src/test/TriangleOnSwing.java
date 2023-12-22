package test;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

public class TriangleOnSwing extends JFrame {
    private GLJPanel gljPanel;

    public TriangleOnSwing() {
        setTitle("Triangle on Swing");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gljPanel = new GLJPanel(new GLCapabilities(GLProfile.get(GLProfile.GL4)));
        gljPanel.addGLEventListener(new MyGLEventListener());

        getContentPane().add(gljPanel);

        FPSAnimator animator = new FPSAnimator(gljPanel, 60);
        animator.start();
    }

    private static class MyGLEventListener implements GLEventListener {
        private int programID;

        @Override
        public void init(GLAutoDrawable drawable) {
            GL4 gl = drawable.getGL().getGL4();

            String vertexShaderSource = "#version 410\n" + "void main() {" + "  gl_Position = vec4(0.0, 0.5, 0.0, 1.0);"
                    + "}";
            int vertexShader = gl.glCreateShader(GL4.GL_VERTEX_SHADER);
            gl.glShaderSource(vertexShader, 1, new String[] { vertexShaderSource }, null, 0);
            gl.glCompileShader(vertexShader);

            String fragmentShaderSource = "#version 410\n" + "out vec4 fragColor;" + "void main() {"
                    + "  fragColor = vec4(1.0, 0.0, 0.0, 1.0);" + "}";
            int fragmentShader = gl.glCreateShader(GL4.GL_FRAGMENT_SHADER);
            gl.glShaderSource(fragmentShader, 1, new String[] { fragmentShaderSource }, null, 0);
            gl.glCompileShader(fragmentShader);

            programID = gl.glCreateProgram();
            gl.glAttachShader(programID, vertexShader);
            gl.glAttachShader(programID, fragmentShader);
            gl.glLinkProgram(programID);

            // gl.glDeleteShader(vertexShader);
            // gl.glDeleteShader(fragmentShader);
        }

        @Override
        public void dispose(GLAutoDrawable drawable) {
            // リソースの解放処理
        }

        @Override
        public void display(GLAutoDrawable drawable) {
            GL4 gl = drawable.getGL().getGL4();
            gl.glUseProgram(programID);
            gl.glDrawArrays(GL4.GL_TRIANGLES, 0, 3);
        }

        @Override
        public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            // ウィンドウサイズの変更時の処理
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TriangleOnSwing triangleOnSwing = new TriangleOnSwing();
            triangleOnSwing.setVisible(true);
        });
    }
}