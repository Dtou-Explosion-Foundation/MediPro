package medipro.titlemenu;

import java.awt.Graphics2D;
import java.awt.Font;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

import java.awt.Color;

public class TitleMenuView extends GameObjectView{

    public TitleMenuView(TitleMenuModel model){
        super(model);
    }

    @Override
    public void draw(GameObjectModel model, Graphics2D g) {
        TitleMenuModel titleMenuModel = (TitleMenuModel) model;
        String[] menuItems = titleMenuModel.getMenuItems();
        for (int i = 0; i < menuItems.length; i++) {
            g.setFont(new Font("SansSerif",Font.BOLD,50));
            if(i == titleMenuModel.getSelectedItem()) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }
            g.drawString(menuItems[i], -400, 100 + i*50);
        }
    }
}
