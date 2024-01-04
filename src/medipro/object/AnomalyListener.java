package medipro.object;

public interface AnomalyListener {
    public boolean canAnomalyOccurred();

    public void onAnomalyOccurred(int level);

    public void onAnomalyFinished();

    public int minAnomalyLevel();

    public int maxAnomalyLevel();

    public int getOccuredChance();

}