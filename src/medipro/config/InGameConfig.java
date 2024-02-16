package medipro.config;

/**
 * ゲームの設定を保存するクラス.
 */
public final class InGameConfig {

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
     * OpenGLを使用するかどうか.
     */
    public static final boolean USE_OPENGL = true;
    /**
     * 一秒あたりのフレーム数.
     */
    public static final int FPS = 60;
    /**
     * ゲームの速度.
     */
    public static final double GAME_SPEED = 1.0;

    public static final double CHANCE_OF_ANOMALY = 0.6;
}