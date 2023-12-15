package medipro.object.example.grid;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gridobject.GridObjectView;

public class ExampleGridView extends GridObjectView {

    @Override
    public void drawGrid(GameObjectModel model, Graphics2D g, Rectangle grid, int gridX, int gridY) {
        ExampleGridModel gridModel = (ExampleGridModel) model;
        if (gridModel.image.isPresent()) {
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
