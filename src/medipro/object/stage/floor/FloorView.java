package medipro.object.stage.floor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gridobject.GridObjectModel;
import medipro.object.base.gridobject.GridObjectView;

public class FloorView extends GridObjectView {

    private BufferedImage texture;
    private Color color = new Color(45, 45, 45);

    public FloorView(GridObjectModel model) {
        super(model);
        try {
            texture = ImageIO.read(new File("img/floor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawGrid(GameObjectModel model, Graphics2D g, Rectangle grid, int gridX, int gridY) {
        if (gridY == 0) {
            g.drawImage(texture, grid.x, grid.y, grid.width, grid.height, null);
        } else if (gridY < 0) {
            g.setColor(color);
            g.fillRect(grid.x, grid.y, grid.width, grid.height);
        }
    }

}
