package medipro.object.manager.gamemanager;

import java.awt.event.KeyListener;

import medipro.gui.panel.GamePanel;
import medipro.object.AnomalyListener;
import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.PlayWorld;

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

    /**
     * ゲームが一時停止中かどうか.
     */
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
     * 初期の階に移動する.
     */
    public static void resetFloor() {
        GameManagerModel.setFloor(0);
        GameManagerModel.setFloorChangingState(FloorChangingState.LEFT_UP);
    }

    /**
     * 次の階層に進む.
     * 
     * @param isRight 右側に移動するかどうか
     */
    public void nextFloor(boolean isRight) {
        GameManagerModel.floor++;
        KeyListener[] allKeyListeners = this.getWorld().getPanel().getKeyListeners();
        for (KeyListener keyListener : allKeyListeners) {
            this.getWorld().getPanel().removeKeyListener(keyListener);
        }
        setFloorChangingState(FloorChangingState.from(isRight, true));
        regenerateWorld();
    }

    /**
     * 前の階層に戻る.
     * 
     * @param isRight 右側に移動するかどうか
     */
    public void prevFloor(boolean isRight) {
        GameManagerModel.floor++;
        KeyListener[] allKeyListeners = this.getWorld().getPanel().getKeyListeners();
        for (KeyListener keyListener : allKeyListeners) {
            this.getWorld().getPanel().removeKeyListener(keyListener);
        }
        setFloorChangingState(FloorChangingState.from(isRight, false));
        regenerateWorld();
    }

    private void regenerateWorld() {
        World newWorld = new PlayWorld(this.getWorld().getPanel());
        GamePanel panel = this.getWorld().getPanel();
        panel.setWorld(newWorld);
    }

    /**
     * ゲームが一時停止中かどうかを取得する.
     * 
     * @return ゲームが一時停止中かどうか
     */
    public static boolean isPause() {
        return GameManagerModel.isPause == 0;
    }

    /**
     * ゲームが一時停止中かどうかを設定する.
     * 
     * @return ゲームが一時停止中かどうか
     */
    public static int getPause() {
        return GameManagerModel.isPause;
    }

    /**
     * ゲームを一時停止する.
     */
    public static void pause() {
        GameManagerModel.isPause = 0;
    }

    /**
     * ゲームを一時停止を解除する.
     */
    public static void unPause() {
        GameManagerModel.isPause = 1;
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

    /**
     * フロア移動の状態.
     */
    public enum FloorChangingState {
        /** 左から上に移動した. */
        LEFT_UP,
        /** 左から下に移動した. */
        LEFT_DOWN,
        /** 右から上に移動した. */
        RIGHT_UP,
        /** 右から下に移動した. */
        RIGHT_DOWN;

        /**
         * 次のフロアで、左側にいるとき上に移動できるかどうか.
         * 
         * @return 上に移動できるかどうか
         */
        public boolean isUpWhenOnLeft() {
            return this == LEFT_UP || this == RIGHT_DOWN;
        }

        /**
         * 次のフロアで、右側にいるとき上に移動できるかどうか.
         * 
         * @return 上に移動できるかどうか
         */
        public boolean isUpWhenOnRight() {
            return this == RIGHT_UP || this == LEFT_DOWN;
        }

        /**
         * 次のフロアで上に移動できるかどうか.
         * 
         * @param onRight 右側にいるかどうか
         * @return 上に移動できるかどうか
         */
        public boolean isUpWhenOn(boolean onRight) {
            return onRight ? isUpWhenOnRight() : isUpWhenOnLeft();
        }

        /**
         * 上に移動したか.
         * 
         * @return 上に移動したか.
         */
        public boolean isUp() {
            return this == LEFT_UP || this == RIGHT_UP;
        }

        /**
         * 右側から移動したか.
         * 
         * @return 右側から移動したか.
         */
        public boolean isRight() {
            return this == RIGHT_DOWN || this == RIGHT_UP;
        }

        /**
         * Y軸を反転させた状態を取得する.
         * 
         * @return Y軸を反転させた状態
         */
        public FloorChangingState reverseY() {
            return from(isRight(), !isUp());
        }

        /**
         * FloorChangingStateを取得する.
         * 
         * @param isRight 右側から移動したか
         * @param isUp    上に移動したか
         * @return FloorChangingState
         */
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
     * 
     * @return 移動できるかどうか
     */
    public static boolean canGoPrevFloor() {
        return floor > 0;
    }
}
