package medipro.gui.panel;

import medipro.gui.frame.GameFrame;
import medipro.object.base.World;

public interface IGamePanel {
    public void update(double deltaTime);

    public GameFrame getFrame();

    public boolean shouldInvokeUpdate();

    public void setWorld(World world);
}
