package medipro.titlemenu.titlebackground;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import medipro.object.base.gameobject.GameObjectView;
import medipro.util.ImageUtil;

public class TitleBackgroundView extends GameObjectView {
    /**
     * 背景画像データのバッファ.
     */
    private BufferedImage heroImage;

    /**
     * タイトルバックグラウンドのビューを生成.
     * 
     * @param model 対象のモデル
     */
    public TitleBackgroundView(TitleBackgroundModel model) {
        super(model);
        TitleBackgroundModel titleBackgroundModel = (TitleBackgroundModel) model;
        // try {
        // heroImage = ImageIO.read(new File(titleBackgroundModel.getImgPath()));
        heroImage = ImageUtil.loadImage(titleBackgroundModel.getImgPath()).orElse(null);
        // } catch (IOException e) {
        //     logger.severe("Failed to load background image");
        // }
    }

    @Override
    public void draw(Graphics2D g) {
        //TitleBackgroundModel titleBackgroundModel = (TitleBackgroundModel) model;
        if (heroImage != null)
            g.drawImage(heroImage, -512, -384, null);
    }
}
