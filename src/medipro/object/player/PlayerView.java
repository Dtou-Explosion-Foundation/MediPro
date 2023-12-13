package medipro.object.player;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

public class PlayerView extends GameObjectView {
    Image sprites[] = new Image[4];
    final int SPRITE_WIDTH = 64;
    final int SPRITE_HEIGHT = 64;

    public PlayerView(GameObjectModel model) {
        super(model);
        try {
            sprites[0] = ImageIO.read(new File("img/character/bear0.png"));
            sprites[1] = ImageIO.read(new File("img/character/bear1.png"));
            sprites[2] = ImageIO.read(new File("img/character/bear2.png"));
            sprites[3] = ImageIO.read(new File("img/character/bear1.png"));
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        PlayerModel playerModel = (PlayerModel) model;
        Image image = sprites[playerModel.spritesIndex];
        if (playerModel.direction == -1) {
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            g.drawImage(op.filter((BufferedImage) image, null), playerModel.x - SPRITE_WIDTH / 2,
                    playerModel.y - SPRITE_HEIGHT, null);
        } else {
            g.drawImage(image, playerModel.x - SPRITE_WIDTH / 2, playerModel.y - SPRITE_HEIGHT, null);
        }
    }
}
