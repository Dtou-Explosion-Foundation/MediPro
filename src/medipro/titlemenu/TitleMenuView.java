package medipro.titlemenu;

import java.awt.Graphics2D;

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
        logger.info("Title menu Draw");
        for (int i = 0; i < menuItems.length; i++) {
            if(i == titleMenuModel.getSelectedItem()) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }
            g.drawString(menuItems[i], 0, 0 + i*20);
        }
    }
}
