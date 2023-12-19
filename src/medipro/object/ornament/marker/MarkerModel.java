package medipro.object.ornament.marker;

import java.awt.Color;

import medipro.object.base.World;
import medipro.object.base.gameobject.GameObjectModel;

/**
 * マーカーのモデル.
 */
public class MarkerModel extends GameObjectModel {
    /**
     * マーカーのモデルを生成する.
     * 
     * @param world オブジェクトが存在するワールド
     */
    public MarkerModel(World world) {
        super(world);
    }

    /**
     * マーカーの半径.
     */
    public int radius = 5;
    /**
     * マーカーの色.
     */
    public Color color = Color.RED;
}
