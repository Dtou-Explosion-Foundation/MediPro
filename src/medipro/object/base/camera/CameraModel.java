package medipro.object.base.camera;

import java.awt.geom.AffineTransform;

import medipro.config.InGameConfig;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

/**
 * カメラのモデルを実装するクラス.
 */
public class CameraModel extends GameObjectModel {
    /**
     * カメラモデルを生成する.
     * 
     * @param world オブジェクトが存在するワールド
     */
    public CameraModel(final World world) {
        super(world);
    }

    /**
     * カメラのズーム倍率.
     */
    public double scale = 1;

    /**
     * グローバル座標からカメラ座標に変換するアフィン変換行列を取得する. ローカル座標とは違い,カメラの中心を原点とする.
     * 
     * @return AffineTransform
     */
    @Override
    public AffineTransform getTransformMatrix() {
        AffineTransform transform = new AffineTransform();
        transform.translate(InGameConfig.WINDOW_WIDTH / 2, InGameConfig.WINDOW_HEIGHT / 2);
        transform.scale(scale, scale);
        transform.translate(-x, y);
        return transform;
    }

}
