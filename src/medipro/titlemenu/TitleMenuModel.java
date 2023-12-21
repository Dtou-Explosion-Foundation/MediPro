package medipro.titlemenu;

import java.util.logging.Logger;

public class TitleMenuModel {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    private String[] menuItems = {"Start Game", "Options","Quit"};
    private int selectedItem = 0;

    public TitleMenuModel(){
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
