package medipro.titlemenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import medipro.object.base.gameobject.GameObjectView;

/**
 * タイトルメニューのビュー.
 */
public class TitleMenuView extends GameObjectView {

    /**
     * タイトルメニューのビューを生成する.
     * 
     * @param model 対象のモデル
     */
    public TitleMenuView(TitleMenuModel model) {
        super(model);
    }

    @Override
    public void draw(Graphics2D g) {
        TitleMenuModel titleMenuModel = (TitleMenuModel) model;
        String[] menuItems = titleMenuModel.getMenuItems();
        for (int i = 0; i < menuItems.length; i++) {
            g.setFont(new Font("SansSerif", Font.BOLD, 50));
            if (i == titleMenuModel.getSelectedItem()) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.lightGray);
            }
            g.drawString(menuItems[i], 0, 100 + i * 100);
        }
    }

    /**
     * 最後に選択されたメニュー項目.
     */
    int lastSelectedItem = -1;

    @Override
    protected boolean needUpdateTexture() {
        int selectedItem = ((TitleMenuModel) model).getSelectedItem();
        if (lastSelectedItem != selectedItem) {
            lastSelectedItem = selectedItem;
            return true;
        }
        return false;
    }
}
