package medipro.config;

/**
 * ゲームの設定を保存するクラス.動作中に変更される可能性がある（ようにするかもしれない）.
 */
public final class InGameConfig {
    protected InGameConfig() {
        // インスタンス化を防ぐためのコンストラクタ
        throw new UnsupportedOperationException();
    }

    /**
     * ウィンドウの幅の既定値.
     */
    public static final int WINDOW_WIDTH_BASE = 1024;
    /**
     * ウィンドウの高さの既定値.
     */
    public static final int WINDOW_HEIGHT_BASE = 768;
    /**
     * ウィンドウの拡大率.
     */
    public static final float WINDOW_SCALE_RATIO = 1.25f;
    /**
     * ウィンドウの幅.
     */
    public static final int WINDOW_WIDTH = (int) (WINDOW_WIDTH_BASE * WINDOW_SCALE_RATIO);
    /**
     * ウィンドウの高さ.
     */
    public static final int WINDOW_HEIGHT = (int) (WINDOW_HEIGHT_BASE * WINDOW_SCALE_RATIO);
    /**
     * 一秒あたりのフレーム数.
     */
    public static final int FPS = 60;
    /**
     * ゲームの速度.
     */
    public static final double GAME_SPEED = 1.0;
    /**
     * 異変が発生する確率.
     */
    public static final double CHANCE_OF_ANOMALY = 0.6;
}
