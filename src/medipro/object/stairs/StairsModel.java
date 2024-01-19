package medipro.object.stairs;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

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
     * 階段が左上に上れるかどうかを取得する.
     * 
     * @return 階段が左上に上れるかどうか
     */
    public boolean isLeftUp() {
        return this.x < 0;
    }

    /**
     * 階段が左上に上れるかどうかを設定する.
     * 
     * @param isLeftUp 階段が左上に上れるかどうか
     * @deprecated 階段の向きはx座標で判断するようになったため、このメソッドは廃止されました。
     */
    public void setLeftUp(boolean isLeftUp) {
        // this.isLeftUp = isLeftUp;
    }

    /**
     * 階段のモデルを生成する.
     * 
     * @param world ワールド
     */
    public StairsModel(World world) {
        super(world);
    }

}
