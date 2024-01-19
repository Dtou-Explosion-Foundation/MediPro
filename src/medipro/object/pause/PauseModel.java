package medipro.object.pause;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

public class PauseModel extends GameObjectModel{
    private String[] pauseMenuItems = { "再開", "やり直す", "ゲームをやめる" };
    private int selectedItem = 0;

    public PauseModel(World world){
        super(world);
        logger.info("pause game");
    }

    public void nextItem() {
        selectedItem = (selectedItem + 1) % pauseMenuItems.length;
    }

    public void prevItem() {
        selectedItem = (selectedItem + pauseMenuItems.length - 1) % pauseMenuItems.length;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int n){
        selectedItem = n;
    }

    public String[] getMenuItems() {
        return pauseMenuItems;
    }
}
