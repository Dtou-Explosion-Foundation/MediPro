package medipro.result;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import medipro.object.base.gameobject.GameObjectView;
import medipro.object.manager.gamemanager.GameManagerModel;

public class ResultView extends GameObjectView {
    public ResultView(ResultModel model) {
        super(model);
    }

    @Override
    public void draw(Graphics2D g) {
        ResultModel resultModel = (ResultModel) model;
        String[] menuItems = resultModel.getMenuItems();
        int floor = GameManagerModel.getFloor();
        g.setFont(new Font("SansSerif", Font.BOLD, 40));
        g.drawString("あなたが攻略した階層の数は", 0, -250);
        g.setFont(new Font("SansSerif", Font.BOLD, 150));
        g.drawString(String.valueOf(floor) + "層", 250, 50);
        for (int i = 0; i < menuItems.length; i++) {
            g.setFont(new Font("SansSerif", Font.BOLD, 50));
            if (i == resultModel.getSelectedItem()) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.lightGray);
            }
            g.drawString(menuItems[i], 125 + 200 * i, 200);
        }
    }

    int lastSelectedItem = -1;

    @Override
    protected boolean needUpdateTexture() {
        int selectedItem = ((ResultModel) model).getSelectedItem();
        if (lastSelectedItem != selectedItem) {
            lastSelectedItem = selectedItem;
            return true;
        }
        return false;
    }
}
