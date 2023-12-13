package medipro.object.background;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

public class TileView extends GameObjectView {

    public TileView() {
        super();
        logger.info("TileView created");

    }

    @Override
    public void draw(GameObjectModel model, Graphics2D g) {
        logger.info("TileView.draw() called");
        TileModel tileModel = (TileModel) model;
        if (model.world.camera.isPresent()) {
            Point2D.Double[] points = model.world.camera.get().getVisibleArea();
            Rectangle2D.Double bounds = getEnclosingRectangle(points[0], points[1], points[2], points[3]);

            double tileWidth = tileModel.width * tileModel.scaleX;
            double tileHeight = tileModel.height * tileModel.scaleY;

            for (int ix = (int) (bounds.x / tileWidth); ix < (int) (bounds.x
                    + bounds.width / tileWidth); ix += tileWidth) {
                for (int iy = (int) (bounds.x / tileHeight); iy < (int) (bounds.x
                        + bounds.width / tileHeight); iy += tileHeight) {
                    logger.info(
                            "ix: " + ix + ", iy: " + iy + ", tileWidth: " + tileWidth + ", tileHeight: " + tileHeight);
                    g.drawImage(((TileModel) model).image, ix, iy, tileModel.width, tileModel.height, null);
                }
            }

        }
    }

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
