package medipro.object.player;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;
import medipro.util.ImageUtil;

/**
 * プレイヤーのビュー.
 */
public class PlayerView extends GameObjectView {
    /**
     * アニメーション用のスプライトの配列.
     */
    private BufferedImage sprites[];

    /**
     * プレイヤービューを生成する.
     * 
     * @param model 対象のモデル
     */
    public PlayerView(GameObjectModel model) {
        super(model);
        PlayerModel playerModel = (PlayerModel) model;
        sprites = new BufferedImage[playerModel.imagePaths.length * 2];
        try {
            for (int i = 0; i < playerModel.imagePaths.length; i++) {
                sprites[i * 2] = ImageUtil.loadImage(playerModel.imagePaths[i]).orElse(null);
                if (sprites[i * 2] == null)
                    continue;
                AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
                tx.translate(-sprites[i * 2].getWidth(null), 0);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                sprites[i * 2 + 1] = op.filter(sprites[i * 2], null);
            }
        } catch (Exception e) {
            logger.warning(e.toString());
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void draw(Graphics2D g) {
        PlayerModel playerModel = (PlayerModel) model;
        BufferedImage sprite = sprites[playerModel.animations[playerModel.animationIndex] * 2
                + (playerModel.direction == -1 ? 1 : 0)];
        if (sprite != null) {
            if (playerModel.hasDummies()) {
                g.drawImage(sprite, (int) (-getSpriteWidth() / 2) + 40, -(int) getSpriteHeight(), null);
                g.drawImage(sprite, (int) (-getSpriteWidth() / 2) - 40, -(int) getSpriteHeight(), null);
            }
            g.drawImage(sprite, (int) (-getSpriteWidth() / 2), -(int) getSpriteHeight(), null);
        }
    }

    @Override
    protected float getSpriteWidth() {
        return 64;
    }

    @Override
    protected float getSpriteHeight() {
        return 64;
    }

}
