package medipro.object.background;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

public class BackgroundView extends GameObjectView {
    private Image image;
    private int x;
    
    public BackgroundView(GameObjectModel model) {
        super(model);
        x =(int) model.x;
        try {
            image = ImageIO.read(new File("img/background/background_sample.png"));
        } catch(Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }
    
    @Override
    public void draw(GameObjectModel model,Graphics2D g) {
        g.drawImage(image, x, -250,1024,768, null);
        g.drawImage(image, x -1024, -250,1024,768, null);
    }
}
