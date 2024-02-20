package medipro.titlemenu;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

/**
 * タイトルメニューのモデル.
 */
public class TitleMenuModel extends GameObjectModel {
    /**
     * メニュー項目.
     */
    private String[] menuItems = { "Start Game", "Quit" };
    /**
     * 選択中のメニュー項目.
     */
    private int selectedItem = 0;

    /**
     * 作品名.
     */
    private String gameTitle = "実験棟出口（仮）";

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

    /**
     * メニュー項目を選択する.
     * 
     * @param n 選択するメニュー項目
     */
    public void setSelectedItem(int n) {
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

    /**
     * 作品名を取得する.
     * 
     * @return 作品名
     */
    public String getGameTitle() {
        return gameTitle;
    }

}
