package medipro.object.background;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

public class BackgroundModel extends GameObjectModel {
    private int x = 0;

    public BackgroundModel(World world){
        super(world);
        logger.info("BackgroundModel created");
    }

    public void setx(int a){
        x = a;
    }

    public int getx(){
        return x;
    }
}
