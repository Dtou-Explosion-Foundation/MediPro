package medipro.object.manager.gamemanager;

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
    public void nextFloor() {
        GameManagerModel.floor++;
    }

    /**
     * 前の階層に戻る.
     */
    public void prevFloor() {
        GameManagerModel.floor--;
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
}
