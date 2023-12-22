package medipro.object.base.camera;

import java.awt.Graphics2D;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

/**
 * カメラのビューを実装するクラス.
 */
public class CameraView extends GameObjectView {

    /**
     * カメラビューを生成する.
     * 
     * @param model 格納するモデル
     */
    public CameraView(GameObjectModel... models) {
        super(models);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(GameObjectModel model, Graphics2D g) {
    }

}
