package medipro.titlemenu;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import medipro.gui.panel.GamePanel;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.TestWorld;
import medipro.world.World;

public class TitleMenuController extends GameObjectController implements KeyListener {

    public TitleMenuController(TitleMenuModel model) {
        super(model);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        TitleMenuModel model = (TitleMenuModel) models.get(0);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                model.prevItem();
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                model.nextItem();
                break;
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_Z:
                switch (model.getSelectedItem()) {
                    case 0:
                        // model.world.setWorld((World)new TestWorld(model.world.getPanel()));
                        GamePanel gamePanel = (GamePanel)(model.world.getPanel());
                        gamePanel.setWorld(new TestWorld(gamePanel));
                        System.out.println("You selected: " + model.getSelectedItem());
                        break;
                    case 1:
                        System.out.println("You selected: " + model.getSelectedItem());
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
    public void update(GameObjectModel model, float dt) {
        
    }
}