package medipro.object.stage.ceil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gridobject.GridObjectModel;
import medipro.object.base.gridobject.GridObjectView;

/**
 * 天井のビュー.
 */
public class CeilView extends GridObjectView {

    /**
     * テクスチャの範囲外の色.
     */
    private Color color = new Color(45, 45, 45);

    /**
     * 天井のビューを生成する.
     * 
     * @param model 対象のモデル
     */
    public CeilView(GridObjectModel model) {
        super(model);

    }

    @Override
    public void drawGrid(GameObjectModel model, Graphics2D g, Rectangle grid, int gridX, int gridY) {
        CeilModel floorModel = (CeilModel) model;
        if (gridY == 0 && floorModel.getTexutre().isPresent()) {
            g.drawImage(floorModel.getTexutre().get(), grid.x, grid.y, grid.width, grid.height, null);
        } else if (gridY > 0) {
            g.setColor(color);
            g.fillRect(grid.x, grid.y, grid.width, grid.height);
        }
    }
}
