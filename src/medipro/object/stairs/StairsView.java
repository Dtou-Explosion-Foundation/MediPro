package medipro.object.stairs;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

public class StairsView extends GameObjectView {

    private BufferedImage[] textures = null;

    public StairsView(GameObjectModel model) {
        super(model);
        try {
            textures = new BufferedImage[2];
            textures[0] = ImageIO.read(new File("img/stairs.png"));
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-textures[0].getWidth(), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            textures[1] = op.filter(textures[0], null);
        } catch (IOException | NullPointerException e) {
            logger.warning("Failed to load texture: img/stairs.png");
        }

        StairsModel stairsModel = (StairsModel) model;
        stairsModel.setWidth(getSpriteWidth());
        stairsModel.setHeight(getSpriteHeight());
    }

    @Override
    protected float getSpriteWidth() {
        return textures[0].getWidth(null);
    }

    @Override
    protected float getSpriteHeight() {
        return textures[0].getHeight(null);
    }

    @Override
    protected void draw(Graphics2D g) {
        StairsModel stairsModel = (StairsModel) model;
        if (textures != null) {
            g.drawImage(textures[stairsModel.isLeftUp() ? 1 : 0], (int) (-getSpriteWidth() / 2),
                    (int) (-getSpriteHeight() / 2), null);
        }
    }

}
