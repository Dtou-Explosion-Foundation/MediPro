package medipro.object.player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;

public class PlayerController extends GameObjectController implements KeyListener {

    byte keyStateX = 0;

    public PlayerController(GameObjectModel... models) {
        super(models);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        switch (k) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                for (GameObjectModel model : models) {
                    ((PlayerModel) model).moveLeft();
                }
                keyStateX = -1;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                for (GameObjectModel model : models) {
                    ((PlayerModel) model).moveRight();
                }
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
    public void update(GameObjectModel model, float dt) {
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
        playerModel.updateMovement(dt);
        playerModel.updateAnimation(dt);
    }
}