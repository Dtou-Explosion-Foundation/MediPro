package medipro.object.base.gridobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import medipro.object.base.camera.CameraModel;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

public abstract class GridObjectView extends GameObjectView {

    public GridObjectView() {
        super();
    }

    @Override
    public void draw(GameObjectModel model, Graphics2D g) {
        // logger.info("TileView.draw() called");
        g.setTransform(model.world.camera.get().getTransformMatrix());
        GridObjectModel gridModel = (GridObjectModel) model;
        Rectangle2D.Double bounds;
        if (model.world.camera.isPresent()) {
            CameraModel cameraModel = model.world.camera.get();
            Point2D.Double[] points;
            try {
                points = cameraModel.getVisibleArea();
            } catch (NoninvertibleTransformException e) {
                logger.warning("Unable to get visible area, skip drawing tile");
                return;
            }
            bounds = getEnclosingRectangle(points[0], points[1], points[2], points[3]);
        } else {
            bounds = new Rectangle2D.Double(0, 0, model.world.panel.getWidth(), model.world.panel.getHeight());
        }

        int gridWidth = (int) (gridModel.width * gridModel.scaleX);
        int gridHeight = (int) (gridModel.height * gridModel.scaleY);

        int originX = (int) (bounds.x / gridWidth) * gridWidth
                + (int) (model.x % gridWidth);

        int originY = (int) (bounds.y / gridHeight) * gridHeight
                + (int) (model.y % gridHeight);

        for (int ix = -2; ix < bounds.width / gridWidth + 2; ix++) {
            for (int iy = -2; iy < bounds.height / gridHeight + 2; iy++) {
                AffineTransform transform = g.getTransform();
                g.translate(originX + ix * gridWidth, originY + iy * gridHeight);
                this.drawGrid(gridModel, g, new Rectangle(0, 0, gridWidth, gridHeight));
                g.setTransform(transform);
            }
        }
    }

    public abstract void drawGrid(GameObjectModel model, Graphics2D g, Rectangle grid);

    private static Rectangle2D.Double getEnclosingRectangle(Point2D.Double point1, Point2D.Double point2,
            Point2D.Double point3,
            Point2D.Double point4) {
        double minX = Math.min(Math.min(point1.x, point2.x), Math.min(point3.x, point4.x));
        double maxX = Math.max(Math.max(point1.x, point2.x), Math.max(point3.x, point4.x));
        double minY = Math.min(Math.min(point1.y, point2.y), Math.min(point3.y, point4.y));
        double maxY = Math.max(Math.max(point1.y, point2.y), Math.max(point3.y, point4.y));

        double width = maxX - minX;
        double height = maxY - minY;

        return new Rectangle2D.Double(minX, minY, width, height);
    }

}
