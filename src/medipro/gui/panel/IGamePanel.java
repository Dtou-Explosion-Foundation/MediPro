package medipro.gui.panel;

import medipro.gui.frame.GameFrame;

public interface IGamePanel {
    public void update(double deltaTime);

    public GameFrame getFrame();
}
