package medipro.titlemenu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import medipro.gui.panel.IGamePanel;
import medipro.object.base.gameobject.GameObjectController;
import medipro.world.TestWorld;

public class TitleMenuController extends GameObjectController implements KeyListener {

    public TitleMenuController(TitleMenuModel model) {
        super(model);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        TitleMenuModel titleMenuModel = (TitleMenuModel) model;
        switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
        case KeyEvent.VK_W:
            titleMenuModel.prevItem();
            break;
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_S:
            titleMenuModel.nextItem();
            break;
        case KeyEvent.VK_ENTER:
        case KeyEvent.VK_Z:
            switch (titleMenuModel.getSelectedItem()) {
            case 0:
                // model.world.setWorld((World)new TestWorld(model.world.getPanel()));
                JPanel panel = titleMenuModel.world.getPanel();
                IGamePanel gamePanel = (IGamePanel) (panel);
                gamePanel.setWorld(new TestWorld(panel));
                System.out.println("You selected: " + titleMenuModel.getSelectedItem());
                break;
            case 1:
                System.out.println("You selected: " + titleMenuModel.getSelectedItem());
                break;
            case 2:
                System.exit(0);
                break;
            }
            break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void update(double dt) {

    }
}