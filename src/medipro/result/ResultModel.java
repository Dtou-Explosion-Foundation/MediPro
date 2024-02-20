package medipro.result;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

/**
 * リザルトのモデル.
 */
public class ResultModel extends GameObjectModel {
    /**
     *  メニュー項目.
     */
    private String[] menuItems = { "Retry", "Return to title" };
    /**
     * 選択中のメニュー項目.
     */
    private int selectedItem = 0;

    /**
     * リザルトのモデルを生成する.
     * 
     * @param world オブジェクトが存在するワールド.
     */
    public ResultModel(World world) {
        super(world);
        //selectedItem = 0;
        logger.info("Init ResultMenu.");
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

}
