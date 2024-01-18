package medipro.object.stage.floor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gridobject.GridObjectModel;
import medipro.object.base.gridobject.GridObjectView;

/**
 * 床のビュー.
 */
public class FloorView extends GridObjectView {

    /**
     * テクスチャの範囲外の色.
     */
    private Color color = new Color(45, 45, 45);

    /**
     * 床のビューを生成する.
     * 
     * @param model 対象のモデル
     */
    public FloorView(GridObjectModel model) {
        super(model);
    }

    @Override
    public void drawGrid(GameObjectModel model, Graphics2D g, Rectangle grid, int gridX, int gridY) {
        FloorModel floorModel = (FloorModel) model;
        if (gridY == 0 && floorModel.getTexture().isPresent()) {
            g.drawImage(floorModel.getTexture().get(), grid.x, grid.y, grid.width, grid.height, null);
        } else if (gridY < 0) {
            g.setColor(color);
            g.fillRect(grid.x, grid.y, grid.width, grid.height);
        }
    }

}
