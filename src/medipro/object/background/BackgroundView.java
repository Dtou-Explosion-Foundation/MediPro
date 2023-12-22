package medipro.object.background;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

public class BackgroundView extends GameObjectView {
    private Image image;
    
    public BackgroundView(GameObjectModel model) {
        super(model);
        try {
            image = ImageIO.read(new File("img/background/background_sample.png"));
        } catch(Exception e) {
            logger.warning(e.toString());
        }
    }
    
    @Override
    public void draw(GameObjectModel model,Graphics2D g) {
        g.drawImage(image, 0, -250,1024,768, null);
        g.drawImage(image, -1024, -250,1024,768, null);
    }
}
