package medipro.object.manager.gamemanager;

import java.awt.event.KeyListener;

import javax.swing.JPanel;

import medipro.gui.panel.IGamePanel;
import medipro.object.AnomalyListener;
import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * ゲームマネージャのモデル.
 */
public class GameManagerModel extends GameObjectModel {
    /**
     * 現在発生している異変のリスナー.
     */
    private AnomalyListener currentAnomalyListener;

    /**
     * 現在の階層.
     */
    private static int floor = 0;
    private static int isPause = 1;

    /**
     * 現在の階層を取得する.
     * 
     * @return 現在の階層
     */
    public static int getFloor() {
        return floor;
    }

    /**
     * 現在の階層を設定する.
     * 
     * @param floor 現在の階層
     */
    public static void setFloor(int floor) {
        GameManagerModel.floor = floor;
    }

    /**
     * 次の階層に進む.
     */
    public void nextFloor(boolean isRight) {
        GameManagerModel.floor++;
        KeyListener[] allKeyListeners = this.world.getPanel().getKeyListeners();
        for (KeyListener keyListener : allKeyListeners) {
            this.world.getPanel().removeKeyListener(keyListener);
        }
        setFloorChangingState(FloorChangingState.from(isRight, true));
        regenerateWorld();
    }

    /**
     * 前の階層に戻る.
     */
    public void prevFloor(boolean isRight) {
        GameManagerModel.floor++;
        KeyListener[] allKeyListeners = this.world.getPanel().getKeyListeners();
        for (KeyListener keyListener : allKeyListeners) {
            this.world.getPanel().removeKeyListener(keyListener);
        }
        setFloorChangingState(FloorChangingState.from(isRight, false));
        regenerateWorld();
    }

    private void regenerateWorld() {
        World newWorld;
        try {
            newWorld = this.world.getClass().getDeclaredConstructor(JPanel.class).newInstance(this.world.getPanel());
        } catch (Exception e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
            return;
        }
        IGamePanel panel = (IGamePanel) this.world.getPanel();
        panel.setWorld(newWorld);
    }

    public static int getPause() {
        return isPause;
    }

    public static void setPause(int isPause) {
        GameManagerModel.isPause = isPause;
    }

    /**
     * 現在発生している異変のリスナーを取得する.
     * 
     * @return 現在発生している異変のリスナー
     */
    public AnomalyListener getCurrentAnomalyListener() {
        return currentAnomalyListener;
    }

    /**
     * 現在発生している異変のリスナーを取得する.
     * 
     * @return 現在発生している異変のリスナー
     */
    public boolean isAnomalyListenerOccured() {
        return currentAnomalyListener != null;
    }

    /**
     * 現在発生している異変のリスナーを設定する.
     * 
     * @param currentAnomalyListener 現在発生している異変のリスナー
     */
    public void setCurrentAnomalyListener(AnomalyListener currentAnomalyListener) {
        this.currentAnomalyListener = currentAnomalyListener;
    }

    /**
     * ゲームマネージャのモデルを生成する.
     * 
     * @param world 対象のワールド
     */
    public GameManagerModel(World world) {
        super(world);
        logger.info("Floor: " + floor);
    }

    public enum FloorChangingState {
        LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN;

        public boolean isUpWhenOnLeft() {
            return this == LEFT_UP || this == RIGHT_DOWN;
        }

        public boolean isUpWhenOnRight() {
            return this == RIGHT_UP || this == LEFT_DOWN;
        }

        public boolean isUpWhenOn(boolean onRight) {
            return onRight ? isUpWhenOnRight() : isUpWhenOnLeft();
        }

        public boolean isUp() {
            return this == LEFT_UP || this == RIGHT_UP;
        }

        public boolean isRight() {
            return this == RIGHT_DOWN || this == RIGHT_UP;
        }

        public FloorChangingState reverseY() {
            return from(isRight(), !isUp());
        }

        public static FloorChangingState from(boolean isRight, boolean isUp) {
            if (isRight) {
                if (isUp)
                    return FloorChangingState.RIGHT_UP;
                else
                    return FloorChangingState.RIGHT_DOWN;
            } else {
                if (isUp)
                    return FloorChangingState.LEFT_UP;
                else
                    return FloorChangingState.LEFT_DOWN;
            }

        }

    }

    /**
     * 前回のフロア移動の状態.
     */
    private static FloorChangingState floorChangingState = FloorChangingState.LEFT_UP;

    /**
     * 前回のフロア移動の状態を取得する.
     * 
     * @return 前回のフロア移動の状態
     */
    public static FloorChangingState getFloorChangingState() {
        return floorChangingState;
    }

    /**
     * 前回のフロア移動の状態を設定する.
     * 
     * @param floorChangingState 前回のフロア移動の状態
     */
    public static void setFloorChangingState(FloorChangingState floorChangingState) {
        GameManagerModel.floorChangingState = floorChangingState;
    }

    /**
     * 前の階に移動できるかどうか.
     */
    public static boolean canGoPrevFloor() {
        return floor > 0;
    }
}
