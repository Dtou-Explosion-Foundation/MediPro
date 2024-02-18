package medipro.object.stage.stairs;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.manager.gamemanager.GameManagerController;
import medipro.object.manager.gamemanager.GameManagerModel;
import medipro.world.World;

/**
 * 階段のモデル.
 */
public class StairsModel extends GameObjectModel {

    /**
     * 階段の幅.
     */
    private float width;

    /**
     * 階段のトリガー範囲.
     */
    private float triggerRange;

    /**
     * 階段のトリガー範囲を取得する.
     * 
     * @return 階段のトリガー範囲
     */
    public float getTriggerRange() {
        return triggerRange != 0 ? triggerRange : getWidth();
    }

    /**
     * 階段のトリガー範囲を設定する.
     * 
     * @param triggerRange 階段のトリガー範囲
     */
    public void setTriggerRange(float triggerRange) {
        this.triggerRange = triggerRange;
    }

    /**
     * 階段の幅を取得する.
     * 
     * @return 階段の幅
     */
    public float getWidth() {
        return width;
    }

    /**
     * 階段の幅を設定する.
     * 
     * @param width 階段の幅
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * 階段の高さ.
     */
    private float height;

    /**
     * 階段の高さを取得する.
     * 
     * @return 階段の高さ
     */
    public float getHeight() {
        return height;
    }

    /**
     * 階段の高さを設定する.
     * 
     * @param height 階段の高さ
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * 階段が右上に上れるかどうかを取得する.
     * 
     * @return 階段が右上に上れるかどうか
     */
    public boolean isRight() {
        return this.getX() > 0;
    }

    /**
     * 階段が右上に上れるかどうかを設定する.
     * 
     * @param setRight 階段が右上に上れるかどうか
     * @deprecated 階段の向きはx座標で判断するようになったため、このメソッドは廃止されました。
     */
    public void setRight(boolean setRight) {
        // this.setRight = setRight;
    }

    /**
     * 階段のモデルを生成する.
     * 
     * @param world ワールド
     */
    public StairsModel(World world) {
        super(world);
    }

    /**
     * 前の階層に戻れるかどうかを取得する.
     * 
     * @return 前の階層に戻れるかどうか
     */
    public boolean canGoPrevFloor() {
        return GameManagerController.canGoPrevFloor() || isGoingUp();
    }

    /**
     * 階段が上向きかどうかを取得する.
     * 
     * @return 階段が上向きかどうか
     */
    public boolean isGoingUp() {
        return GameManagerModel.getFloorChangingState().reverseY().isUpWhenOn(this.isRight());
    }

}
