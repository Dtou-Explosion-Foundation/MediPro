package medipro.object.stairs;

import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JPanel;

import medipro.gui.panel.IGamePanel;
import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.manager.gamemanager.GameManagerModel;
import medipro.object.player.PlayerController;

public class StairsController extends GameObjectController {

    private PlayerController playerController;

    public StairsController(GameObjectModel model) {
        super(model);
    }

    @Override
    public void postSetupWorld() {
        List<PlayerController> playerControllers = this.model.world.getControllers(PlayerController.class);
        if (playerControllers.size() > 0) {
            this.playerController = playerControllers.get(0);
        }
    }

    private boolean isPlayerGoingUp = false;

    @Override
    public void update(double dt) {
        if (playerController == null)
            return;

        StairsModel stairsModel = (StairsModel) model;
        float handleRange = stairsModel.getWidth() / 2;
        float playerX = (float) playerController.model.x;
        if (!isPlayerGoingUp && playerX > model.x - handleRange && playerX < model.x + handleRange) {
            isPlayerGoingUp = true;
            playerController.pushAutoWalker( // 自動で階段を上る
                    new Point2D.Double(model.x + handleRange * (stairsModel.isLeftUp() ? -1 : 1),
                            model.y + stairsModel.getHeight() / 2),
                    4, t -> t, () -> {
                        GameManagerModel.incrementFloor();
                        World newWorld;
                        try {
                            newWorld = this.model.world.getClass().getDeclaredConstructor(JPanel.class)
                                    .newInstance(this.model.world.getPanel());
                        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                            logger.severe(e.getMessage());
                            e.printStackTrace();
                            return;
                        }
                        IGamePanel panel = (IGamePanel) this.model.world.getPanel();
                        panel.setWorld(newWorld);
                    });
        }
    }
}
