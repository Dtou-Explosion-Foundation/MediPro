package medipro.titlemenu;

import medipro.world.World;

import medipro.object.base.gameobject.GameObjectModel;

public class TitleMenuModel extends GameObjectModel{
    private String[] menuItems = {"Start Game", "Options", "Quit"};
    private int selectedItem = 0;

    public TitleMenuModel(World world){
        super(world);
        logger.info("Init TitleMenu.");
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

    public String[] getMenuItems(){
        return menuItems;
    }
}
