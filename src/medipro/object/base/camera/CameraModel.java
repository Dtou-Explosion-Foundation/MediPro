package medipro.object.base.camera;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import medipro.config.InGameConfig;
import medipro.gui.panel.IGamePanel;
import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * カメラのモデルを実装するクラス.
 */
public class CameraModel extends GameObjectModel {
    /**
     * カメラモデルを生成する.
     * 
     * @param world オブジェクトが存在するワールド
     */
    public CameraModel(World world) {
        super(world);
    }

    /**
     * カメラのズーム倍率.
     */
    private double scale = 1;

    public double getScale() {
        if (InGameConfig.USE_OPENGL)
            return scale;
        else {
            if (this.world.panel instanceof IGamePanel)
                return scale * ((IGamePanel) this.world.panel).getFrame().getScreenScaleFactor();
            else
                return scale;
        }
        // return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    private int ubo = -1;

    /**
     * グローバル座標からカメラ座標に変換するアフィン変換行列を取得する. ローカル座標とは違い,カメラの中心を原点とするので注意.
     * 
     * @return AffineTransform
     */
    @Override
    public AffineTransform getTransformMatrix() {
        double screenScaleFactor = ((IGamePanel) this.world.panel).getFrame().getScreenScaleFactor();
        AffineTransform transform = new AffineTransform();
        // transform.translate(this.world.panel.getWidth() / 2, this.world.panel.getHeight() / 2);
        // transform.translate(InGameConfig.WINDOW_WIDTH / 2, InGameConfig.WINDOW_HEIGHT / 2);
        transform.translate(InGameConfig.WINDOW_WIDTH / 2 * screenScaleFactor,
                InGameConfig.WINDOW_HEIGHT / 2 * screenScaleFactor);
        transform.scale(getScale(), getScale());
        transform.translate(-x, y);
        return transform;
    }

    /**
     * カメラの表示範囲をワールド座標で取得する.
     * 
     * @return カメラの表示範囲を示す4点の座標.
     * @throws NoninvertibleTransformException 逆行列が存在しない場合のエラー.
     */
    public Point2D.Double[] getVisibleArea() throws NoninvertibleTransformException {
        Point2D.Double[] points = new Point2D.Double[4];
        double width = world.panel.getWidth();
        double height = world.panel.getHeight();
        AffineTransform transform = getTransformMatrix().createInverse();
        points[0] = new Point2D.Double(0, 0);
        points[1] = new Point2D.Double(width, 0);
        points[2] = new Point2D.Double(width, height);
        points[3] = new Point2D.Double(0, height);
        // points[0] = new Point2D.Double(this.x, this.y);
        // points[1] = new Point2D.Double(this.x + width, this.y);
        // points[2] = new Point2D.Double(this.x + width, this.y + height);
        // points[3] = new Point2D.Double(this.x, this.y + height);
        for (int i = 0; i < points.length; i++) {
            transform.transform(points[i], points[i]);
        }
        return points;
    }

    public int getUBO() {
        return ubo;
    }

    public void setUBO(int ubo) {
        this.ubo = ubo;
    }

}
