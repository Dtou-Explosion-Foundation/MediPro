package medipro.object.stairs;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;
import medipro.util.ImageUtil;

/**
 * 階段のビュー.
 */
public class StairsView extends GameObjectView {

    /**
     * 階段のテクスチャ.
     */
    private BufferedImage[] textures = null;

    /**
     * 矢印のテクスチャ.
     */
    private BufferedImage[] arrowTextures = null;

    /**
     * 階段のビューを生成する.
     * 
     * @param model 対象のモデル
     */
    public StairsView(GameObjectModel model) {
        super(model);
        textures = new BufferedImage[2];
        textures[0] = ImageUtil.loadImage("img/layers/medipro_0001_Stairs.png").orElse(null);
        if (textures[0] != null) {
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-textures[0].getWidth(), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            textures[1] = op.filter(textures[0], null);
        }

        arrowTextures = new BufferedImage[5];
        arrowTextures[0] = ImageUtil.loadImage("img/arrow/Arrow_RightUp.png").orElse(null);
        arrowTextures[1] = ImageUtil.loadImage("img/arrow/Arrow_RightDown.png").orElse(null);
        arrowTextures[2] = ImageUtil.invertX(arrowTextures[0]);
        arrowTextures[3] = ImageUtil.invertX(arrowTextures[1]);
        arrowTextures[4] = ImageUtil.loadImage("img/arrow/Arrow_RightStraight.png").orElse(null);

        StairsModel stairsModel = (StairsModel) model;
        stairsModel.setWidth(getSpriteWidth());
        stairsModel.setHeight(getSpriteHeight());
    }

    @Override
    protected float getSpriteWidth() {
        return textures[0] != null ? textures[0].getWidth(null) : 0;
    }

    @Override
    protected float getSpriteHeight() {
        return textures[0] != null ? textures[0].getHeight(null) : 0;
    }

    @Override
    protected void draw(Graphics2D g) {
        StairsModel stairsModel = (StairsModel) model;
        if (textures != null) {
            int arrowIndex = 4;
            if (stairsModel.canGoPrevFloor()) {
                g.drawImage(textures[stairsModel.isRight() ? 0 : 1], (int) (-getSpriteWidth() / 2),
                        (int) (-getSpriteHeight() / 2), null);
                arrowIndex = (stairsModel.isGoingUp() ? 0 : 1) + (stairsModel.isRight() ? 0 : 2);
            }
            g.drawImage(arrowTextures[arrowIndex], stairsModel.isRight() ? 10 : -35, 5, null);
        }
    }

}
