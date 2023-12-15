package medipro.object.example.grid;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gridobject.GridObjectView;

public class ExampleGridView extends GridObjectView {

    @Override
    public void drawGrid(GameObjectModel model, Graphics2D g, Rectangle grid) {
        ExampleGridModel gridModel = (ExampleGridModel) model;
        if (gridModel.image.isPresent()) {
            g.drawImage(gridModel.image.get(),
                    grid.x,
                    grid.y,
                    grid.width,
                    grid.height, null);
        }

    }

}
