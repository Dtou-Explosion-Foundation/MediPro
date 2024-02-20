package medipro.object.base.gridobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import medipro.config.InGameConfig;
import medipro.object.base.camera.CameraModel;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

/**
 * グリッドオブジェクトのビュー.
 */
public abstract class GridObjectView extends GameObjectView {

    /**
     * グリッドオブジェクトのビューを生成する.
     * 
     * @param model 対象のモデル
     */
    public GridObjectView(GridObjectModel model) {
        super(model);
    }

    /**
     * 描画範囲内のグリッドを計算し、drawGridを呼び出す.
     * 
     * @param g 描画対象のGraphics2D
     */
    @Override
    public void draw(Graphics2D g) {
        g.setTransform(model.getWorld().getCameraTransform());
        GridObjectModel gridModel = (GridObjectModel) model;
        Rectangle2D.Double bounds;
        if (model.getWorld().getCamera().isPresent()) {
            CameraModel cameraModel = model.getWorld().getCamera().get();
            Point2D.Double[] points;
            try {
                points = cameraModel.getVisibleArea();
            } catch (NoninvertibleTransformException e) {
                logger.warning("Unable to get visible area, skip drawing tile");
                return;
            }
            bounds = getEnclosingRectangle(points[0], points[1], points[2], points[3]);
        } else {
            bounds = new Rectangle2D.Double(0, 0, InGameConfig.WINDOW_WIDTH_BASE, InGameConfig.WINDOW_HEIGHT_BASE);
        }

        // 1グリッドのサイズを計算
        int gridWidth = (int) (gridModel.getWidth() * gridModel.getScaleX());
        int gridHeight = (int) (gridModel.getHeight() * gridModel.getScaleY());

        // グリッドの原点(最左上)を計算
        int originX = (int) (bounds.x / gridWidth) * gridWidth + (int) (model.getX() % gridWidth) - gridWidth / 2;
        int originY = (int) (bounds.y / gridHeight) * gridHeight + (int) (model.getY() % gridHeight) - gridHeight / 2;

        // グリッドの原点の通し番号を計算
        int originGridX = (int) Math.ceil((originX - model.getX()) / gridWidth);
        int originGridY = (int) Math.ceil((originY - model.getY()) / gridHeight);

        for (int ix = -2; ix < bounds.width / gridWidth + 2; ix++) {
            for (int iy = -2; iy < bounds.height / gridHeight + 2; iy++) {
                AffineTransform transform = g.getTransform();
                g.translate(originX + ix * gridWidth, originY + iy * gridHeight);
                this.drawGrid(gridModel, g, new Rectangle(0, 0, gridWidth, gridHeight), originGridX + ix,
                        originGridY + iy);
                g.setTransform(transform);
            }
        }
    }

    /**
     * グリッドを描画する.
     * 
     * @param model 描画対象のモデル.
     * @param g     描画対象のGraphics2D.
     * @param grid  グリッドの範囲.この内部に描画する.
     * @param gridX グリッドのX座標方向のインデックス. 右下が0.
     * @param gridY グリッドのY座標方向のインデックス. 右下が0.
     */
    public abstract void drawGrid(GameObjectModel model, Graphics2D g, Rectangle grid, int gridX, int gridY);

    /**
     * 4点の座標から、その4点を内部に持つ最小の水平な長方形を返す.
     * 
     * @param point1 4点の座標
     * @param point2 4点の座標
     * @param point3 4点の座標
     * @param point4 4点の座標
     * @return 4点を内部に持つ最小の水平な長方形
     */
    private static Rectangle2D.Double getEnclosingRectangle(Point2D.Double point1, Point2D.Double point2,
            Point2D.Double point3, Point2D.Double point4) {
        double minX = Math.min(Math.min(point1.x, point2.x), Math.min(point3.x, point4.x));
        double maxX = Math.max(Math.max(point1.x, point2.x), Math.max(point3.x, point4.x));
        double minY = Math.min(Math.min(point1.y, point2.y), Math.min(point3.y, point4.y));
        double maxY = Math.max(Math.max(point1.y, point2.y), Math.max(point3.y, point4.y));

        double width = maxX - minX;
        double height = maxY - minY;

        return new Rectangle2D.Double(minX, minY, width, height);
    }

}
