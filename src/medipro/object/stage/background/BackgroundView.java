package medipro.object.stage.background;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gridobject.GridObjectModel;
import medipro.object.base.gridobject.GridObjectView;

/**
 * 背景のビュー.
 */
public class BackgroundView extends GridObjectView {

    /**
     * 背景のビューを生成する.
     * 
     * @param model 対象のモデル
     */
    public BackgroundView(GridObjectModel model) {
        super(model);
    }

    @Override
    public void drawGrid(GameObjectModel model, Graphics2D g, Rectangle grid, int gridX, int gridY) {
        BackgroundModel backgroundModel = (BackgroundModel) model;
        if (backgroundModel.image.isPresent()) {
            g.drawImage(backgroundModel.image.get(), grid.x, grid.y, grid.width, grid.height, null);
        }
    }
}
