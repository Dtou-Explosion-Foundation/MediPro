package medipro.titlemenu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class TitleMenuView{
    public void draw(TitleMenuModel model, Graphics2D g) {
        String[] menuItems = model.getMenuItems();
        for (int i = 0; i < menuItems.length; i++) {
            if(i == model.getSelectedItem()) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }
            g.drawString(menuItems[i], 50, 50 + i*20);
        }
    }
}
