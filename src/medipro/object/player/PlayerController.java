package medipro.object.player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

public class PlayerController extends GameObjectController implements KeyListener {
    public PlayerController(GameObjectModel model, GameObjectView view) {
        super(model, view);
    }

    byte keyStateX = 0;

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        PlayerModel playerModel = (PlayerModel) model;

        switch (k) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                playerModel.moveLeft();
                keyStateX = -1;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                playerModel.moveRight();
                keyStateX = 1;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyStateX = 0;
    }

    @Override
    public void update(float dt) {
        PlayerModel playerModel = (PlayerModel) model;
        switch (keyStateX) {
            case -1:
                playerModel.moveLeft();
                break;

            case 1:
                playerModel.moveRight();
                break;

            default:
                break;
        }
        super.update(dt);
    }

}