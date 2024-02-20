package medipro.titlemenu.titlebackground;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

/**
 * タイトルバックグラウンドのモデル.
 */
public class TitleBackgroundModel extends GameObjectModel {
    /**
     * タイトルメニューの背景画像のパス.
     */
    private String imgPath = "img/titlemenu/heroImage.jpg";

    /**
     * タイトルバックグラウンドのモデルを生成する.
     * 
     * @param world オブジェクトが存在するワールド
     */
    public TitleBackgroundModel(World world) {
        super(world);
        logger.info("background set");
    }

    /**
     * 背景画像のパスを取得する.
     * 
     * @return 背景画像のパス.
     */
    public String getImgPath() {
        return imgPath;
    }
}
