package medipro.object.ornament.marker;

import java.awt.Graphics2D;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;

import medipro.object.base.gameobject.GameObjectView;

/**
 * マーカーのビュー.
 */
public class MarkerView extends GameObjectView {

    public MarkerView(MarkerModel model) {
        super(model);
    }

    /**
     * モデルを元に描画を行う. テスト用の図形を描画する.
     * 
     * @param g 描画対象のGraphics2D
     */
    @Override
    public void draw(Graphics2D g) {
        MarkerModel markerModel = (MarkerModel) model;
        g.setColor(markerModel.color);
        // g.fillOval(-markerModel.radius / 2, -markerModel.radius / 2,
        // markerModel.radius,
        g.fillRect(-markerModel.radius / 2, -markerModel.radius / 2, markerModel.radius, markerModel.radius);
    }

    @Override
    protected String getShaderPath(String ext) {
        super.getShaderPath(ext);
        return "shader/Marker/Marker" + "." + ext;
    }

    @Override
    protected float getSpriteWidth() {
        MarkerModel markerModel = (MarkerModel) model;
        return markerModel.radius;
    }

    @Override
    protected float getSpriteHeight() {
        MarkerModel markerModel = (MarkerModel) model;
        return markerModel.radius;
    }

    @Override
    protected boolean needUpdateTexture() {
        return false;
    }

    @Override
    protected void initTextures(GLAutoDrawable drawable) {
    }

    @Override
    protected void initUniforms(GLAutoDrawable drawable) {
        super.initUniforms(drawable);
        MarkerModel markerModel = (MarkerModel) model;
        GL4 gl = drawable.getGL().getGL4();

        int uColorLocation = gl.glGetUniformLocation(shaderProgram, "uColor");
        if (uColorLocation != -1) {
            gl.glUniform4fv(uColorLocation, 1, markerModel.color.getRGBComponents(null), 0);
        }
    }
}
