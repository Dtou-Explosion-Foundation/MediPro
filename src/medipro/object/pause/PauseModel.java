package medipro.object.pause;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

/**
 * ポーズのモデル.
 */
public class PauseModel extends GameObjectModel {
    /**
     * メニュー項目.
     */
    private String[] pauseMenuItems = { "再開", "やり直す", "ゲームをやめる" };
    /**
     * 選択中のメニュー項目.
     */
    private int selectedItem = 0;

    /**
     * ポーズのモデルを選択する.
     * 
     * @param world オブジェクトが存在するワールド.
     */
    public PauseModel(World world) {
        super(world);
        logger.info("pause game");
    }

    /**
     * 次のメニュー項目を選択する.
     */
    public void nextItem() {
        selectedItem = (selectedItem + 1) % pauseMenuItems.length;
    }

    /**
     * 前のメニュー項目を選択する.
     */
    public void prevItem() {
        selectedItem = (selectedItem + pauseMenuItems.length - 1) % pauseMenuItems.length;
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
        return pauseMenuItems;
    }
}
