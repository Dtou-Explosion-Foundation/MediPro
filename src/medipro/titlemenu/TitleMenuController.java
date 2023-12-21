package medipro.titlemenu;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TitleMenuController implements KeyListener {
    private TitleMenuModel model;
    private TitleMenuView view;

    public TitleMenuController(TitleMenuModel model, TitleMenuView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                model.prevItem();
                break;
            case KeyEvent.VK_DOWN:
                model.nextItem();
                break;
            case KeyEvent.VK_ENTER:
                //System.out.println("You selected: " + model.getSelectedItem());
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void render(Graphics2D g) {
        view.draw(model, g);
    }
}