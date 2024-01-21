package medipro.object.overlay.blackfilter;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;

import medipro.config.InGameConfig;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

/**
 * 暗転のビュー.
 */
public class BlackFilterView extends GameObjectView {

    /**
     * 暗転のビューを生成する.
     * 
     * @param model 対象のモデル
     */
    public BlackFilterView(GameObjectModel model) {
        super(model);
    }

    @Override
    protected void draw(Graphics2D g) {
        g.setTransform(new AffineTransform());
        g.setColor(((BlackFilterModel) model).getColor());
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
