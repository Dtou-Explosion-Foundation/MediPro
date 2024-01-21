package medipro.object.ornament.glmarker;

import java.awt.Graphics2D;

import medipro.object.base.gameobject.GameObjectView;

/**
 * GLのテスト用マーカーのビュー.
 */
public class GLMarkerView extends GameObjectView {

    /**
     * GLのテスト用マーカーのビューを生成する.
     * 
     * @param model 対象のモデル
     */
    public GLMarkerView(GLMarkerModel model) {
        super(model);
    }

    @Override
    public void draw(Graphics2D g) {
        GLMarkerModel glMarkerModel = (GLMarkerModel) model;
        g.setColor(glMarkerModel.getColor());
        g.fillOval(-150, -200, 300, 400);
    }

}
