package medipro.object.ornament.glmarker;

import java.awt.Color;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * GLのテスト用マーカーのモデル.
 */
public class GLMarkerModel extends GameObjectModel {

    /**
     * マーカーの色.
     */
    private Color color = Color.RED;

    /**
     * GLのテスト用マーカーのモデルを生成する.
     * 
     * @param world 対象のワールド
     */
    public GLMarkerModel(World world) {
        super(world);
    }

    /**
     * マーカーの色を設定する.
     * 
     * @param color マーカーの色
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * マーカーの色を取得する.
     * 
     * @return マーカーの色
     */
    public Color getColor() {
        return color;
    }
}
