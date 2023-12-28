package medipro.object.example.grid;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gridobject.GridObjectView;

/**
 * グリッドオブジェクトの例のビュー.
 */
public class ExampleGridView extends GridObjectView {

    public ExampleGridView(ExampleGridModel model) {
        super(model);
    }

    /**
     * モデルを元に描画を行う. テスト用の図形と画像を描画する.
     * 
     * @param model 描画対象のモデル.
     * @param g     描画対象のGraphics2D.
     * @param grid  グリッドの範囲.この内部に描画する.
     * @param gridX グリッドのX座標方向のインデックス. 右下が0.
     * @param gridY グリッドのY座標方向のインデックス. 右下が0.
     */
    @Override
    public void drawGrid(GameObjectModel model, Graphics2D g, Rectangle grid, int gridX, int gridY) {
        ExampleGridModel gridModel = (ExampleGridModel) model;
        if (gridModel.image.isPresent()) {
            g.setColor(Color.RED);
            g.drawImage(gridModel.image.get(), grid.x, grid.y, grid.width, grid.height, null);
            g.drawRect(grid.x, grid.y, grid.width, grid.height);
            String str = String.format("(%d, %d)", gridX, gridY);
            char[] chars = str.toCharArray();
            FontMetrics fm = g.getFontMetrics();
            g.drawChars(chars, 0, chars.length, grid.x + (grid.height - fm.stringWidth(str)) / 2,
                    grid.y + grid.height / 2);
        }
    }
}
