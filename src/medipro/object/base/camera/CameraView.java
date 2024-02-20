package medipro.object.base.camera;

import java.awt.Graphics2D;

import medipro.object.base.gameobject.GameObjectView;

/**
 * カメラのビューを実装するクラス.
 */
public class CameraView extends GameObjectView {

    /**
     * カメラビューを生成する.
     * 
     * @param model 対象のモデル
     */
    public CameraView(CameraModel model) {
        super(model);
    }

    @Override
    public void draw(Graphics2D g) {
    }
}
