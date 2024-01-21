package medipro.object.pause;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import medipro.object.base.gameobject.GameObjectView;
import medipro.object.manager.gamemanager.GameManagerModel;

public class PauseView extends GameObjectView{
    public PauseView(PauseModel model){
        super(model);
    }

    @Override
    public void draw(Graphics2D g){
        PauseModel pauseModel = (PauseModel) model;
        g.setTransform(new AffineTransform());
        String [] menuItems = pauseModel.getMenuItems();
        int floor = GameManagerModel.getFloor();
        if(GameManagerModel.getPause()==0){
            g.setFont(new Font("SansSerif", Font.BOLD, 75));
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(floor)+"層", 800, 175);
            g.setFont(new Font("SansSerif", Font.BOLD, 50));
            g.drawString("現在", 700, 100);
            for (int i = 0; i < menuItems.length; i++) {
                if (i == pauseModel.getSelectedItem()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.lightGray);
                }
                g.drawString(menuItems[i], 0, 100 + i * 50);
            }
        }
    }

    int lastSelectedItem = -1;

    @Override
    protected boolean needUpdateTexture() {
        int selectedItem = ((PauseModel) model).getSelectedItem();
        if (lastSelectedItem != selectedItem) {
            lastSelectedItem = selectedItem;
            return true;
        }
        return false;
    }
}
