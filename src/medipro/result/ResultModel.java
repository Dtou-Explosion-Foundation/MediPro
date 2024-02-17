package medipro.result;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

public class ResultModel extends GameObjectModel {
    private String[] menuItems = { "Retry", "Return to title" };
    private int selectedItem = 0;

    public ResultModel(World world) {
        super(world);
        //selectedItem = 0;
        logger.info("Init ResultMenu.");
    }

    public void nextItem() {
        selectedItem = (selectedItem + 1) % menuItems.length;
    }

    public void prevItem() {
        selectedItem = (selectedItem + menuItems.length - 1) % menuItems.length;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int n) {
        selectedItem = n;
    }

    public String[] getMenuItems() {
        return menuItems;
    }

}
