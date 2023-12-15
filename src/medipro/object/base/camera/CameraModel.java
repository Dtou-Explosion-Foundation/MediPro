package medipro.object.base.camera;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import medipro.config.InGameConfig;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

public class CameraModel extends GameObjectModel {

    public CameraModel(World world) {
        super(world);
    }

    public double scale = 1;

    @Override
    public AffineTransform getTransformMatrix() {
        AffineTransform transform = new AffineTransform();
        transform.translate(InGameConfig.WINDOW_WIDTH / 2, InGameConfig.WINDOW_HEIGHT / 2);
        transform.scale(scale, scale);
        transform.translate(-x, y);
        return transform;
    }

    /**
     * カメラの表示範囲をワールド座標で取得する.
     * 
     * @return カメラの表示範囲を示す4点の座標
     * @throws NoninvertibleTransformException
     */
    public Point2D.Double[] getVisibleArea() throws NoninvertibleTransformException {
        Point2D.Double[] points = new Point2D.Double[4];
        double width = InGameConfig.WINDOW_WIDTH;
        double height = InGameConfig.WINDOW_HEIGHT;
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

}
