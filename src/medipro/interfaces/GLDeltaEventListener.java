package medipro.interfaces;

import java.util.EventListener;

import com.jogamp.opengl.GLAutoDrawable;

public interface GLDeltaEventListener extends EventListener {
    void init(GLAutoDrawable drawable);

    void dispose(GLAutoDrawable drawable);

    void display(GLAutoDrawable drawable, double deltaTime);

    void reshape(GLAutoDrawable drawable, int x, int y, int w, int h);
}
