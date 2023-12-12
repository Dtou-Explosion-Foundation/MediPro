package objects.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import objects.base.GameObject.GameObjectController;
import objects.base.GameObject.GameObjectModel;
import objects.base.GameObject.GameObjectView;

public class PlayerController extends GameObjectController implements KeyListener {
    public PlayerController(GameObjectModel model, GameObjectView view) {
        super(model, view);
    }

    byte keyStateX = 0;

    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        PlayerModel playerModel = (PlayerModel) model;

        switch (k) {
            case KeyEvent.VK_LEFT:
                logger.info("left key pressed");
                playerModel.moveLeft();
                keyStateX = -1;
                break;
            case KeyEvent.VK_RIGHT:
                logger.info("right key pressed");
                playerModel.moveRight();
                keyStateX = 1;
                break;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

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