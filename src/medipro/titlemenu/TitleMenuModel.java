package medipro.titlemenu;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * タイトルメニューのモデル.
 */
public class TitleMenuModel extends GameObjectModel {
    /**
     * メニュー項目.
     */
    private String[] menuItems = { "Start Game", "Options", "Quit" };
    /**
     * 選択中のメニュー項目.
     */
    private int selectedItem = 0;

    /**
     * タイトルメニューのモデルを生成する.
     * 
     * @param world オブジェクトが存在するワールド.
     */
    public TitleMenuModel(World world) {
        super(world);
        logger.info("Init TitleMenu.");
    }

    /**
     * 次のメニュー項目を選択する.
     */
    public void nextItem() {
        selectedItem = (selectedItem + 1) % menuItems.length;
    }

    /**
     * 前のメニュー項目を選択する.
     */
    public void prevItem() {
        selectedItem = (selectedItem + menuItems.length - 1) % menuItems.length;
    }

    /**
     * 現在選択中のメニュー項目を取得する.
     * 
     * @return 現在選択中のメニュー項目
     */
    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int n){
        selectedItem = n;
    }

    /**
     * メニュー項目を取得する.
     * 
     * @return メニュー項目
     */
    public String[] getMenuItems() {
        return menuItems;
    }

}
