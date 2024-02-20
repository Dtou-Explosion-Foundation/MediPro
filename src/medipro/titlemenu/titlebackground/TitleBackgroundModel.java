package medipro.titlemenu.titlebackground;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

public class TitleBackgroundModel extends GameObjectModel {
    private String imgPath = "img/titlemenu/heroImage.jpg";

    public TitleBackgroundModel(World world) {
        super(world);
        logger.info("background set");
    }

    public String getImgPath() {
        return imgPath;
    }
}
