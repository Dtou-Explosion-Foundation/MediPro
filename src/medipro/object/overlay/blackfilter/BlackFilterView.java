package medipro.object.overlay.blackfilter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;

import medipro.config.InGameConfig;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

public class BlackFilterView extends GameObjectView {

    public BlackFilterView(GameObjectModel model) {
        super(model);
    }

    @Override
    protected void draw(Graphics2D g) {
        g.setTransform(new AffineTransform());
        g.setColor(new Color(0f, 0f, 0f, (float) ((BlackFilterModel) model).getAlpha()));
        g.fillRect(0, 0, InGameConfig.WINDOW_WIDTH, InGameConfig.WINDOW_HEIGHT);
    }

    @Override
    protected String getShaderPath(String ext) {
        super.getShaderPath(ext);
        return "shader/blackfilter/BlackFilter" + "." + ext;
    }

    @Override
    protected boolean needUpdateTexture() {
        return false;
    }

    @Override
    protected void initTextures(GLAutoDrawable drawable) {

    }

    @Override
    protected void initSamplers(GLAutoDrawable drawable) {

    }

    @Override
    protected void updateUniforms(GLAutoDrawable drawable) {
        BlackFilterModel blackFilterModel = (BlackFilterModel) model;
        GL4 gl = drawable.getGL().getGL4();

        int modelMatUniform = gl.glGetUniformLocation(shaderProgram, "uAlpha");
        if (modelMatUniform != -1) {

            gl.glUniform1f(modelMatUniform, blackFilterModel.getAlpha());
        }
    }
}
