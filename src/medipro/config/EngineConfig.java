package medipro.config;

/**
 * エンジンの設定を保存するクラス.
 */
public class EngineConfig {
    /**
     * レイヤーの数.
     */
    public static final int LAYER_NUM = 128;
    /**
     * デフォルトのレイヤー.
     */
    public static final byte DEFAULT_LAYER = 63;

    public static final int DEFAULT_MONITOR = 0;

    public static final boolean SKIP_TITLE = false;
    public static final int DEBUG_LEVEL = 2; // 0: none, 1: info, 2: debug
}
