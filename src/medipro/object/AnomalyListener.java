package medipro.object;

/**
 * 異常の内容を提供し、異常発生の通知を受け取ることができる.
 */
public interface AnomalyListener {
    /**
     * 異常が発生できるかどうかを返す.
     * 
     * @return 異常が発生できるかどうか
     */
    boolean canAnomalyOccurred();

    /**
     * 異常が発生したときに呼び出される.
     * 
     * @param level 異常のレベル
     */
    void onAnomalyOccurred(int level);

    /**
     * 異常が終了したときに呼び出される.
     */
    void onAnomalyFinished();

    /**
     * 異常の最小レベルを返す.
     * 
     * @return 異常の最小レベル
     */
    int minAnomalyLevel();

    /**
     * 異常の最大レベルを返す.
     * 
     * @return 異常の最大レベル
     */
    int maxAnomalyLevel();

    /**
     * 異常が発生する確率を返す.
     * 
     * @return 異常が発生する確率
     */
    int getOccurredChance();
}
