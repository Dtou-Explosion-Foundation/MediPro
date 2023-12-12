import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JFrame;

import objects.Player.PlayerController;
import objects.Player.PlayerModel;
import objects.Player.PlayerView;

public class Main {
	static final Logger logger = Logger.getLogger("");
	static World mainWorld;

	public static void main(String[] args) {
		setupLogger();
		logger.info("Start game");
		JFrame window = new GameWindow("GameWindow", 1024, 768);
		GamePanel panel = new GamePanel();
		panel.setFocusable(true);
		setupWorld(panel);
		window.add(panel);
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			Boolean isIdle = true;

			public void run() {
				if (isIdle) {
					isIdle = false;
					panel.repaint();
					isIdle = true;
				}
			}
		};

		window.setVisible(true);
		timer.scheduleAtFixedRate(task, 0, (long) (1000f / Config.FPS));
	}

	static void setupWorld(GamePanel panel) {
		mainWorld = new World();
		{
			PlayerModel model = new PlayerModel();
			PlayerView view = new PlayerView(model);
			PlayerController controller = new PlayerController(model, view);
			panel.addKeyListener(controller);
			mainWorld.addController(controller);
		}
	}

	private static void setupLogger() {
		Logger root = Logger.getLogger("");
		Formatter formatter = new SimpleFormatter();
		Handler rootHandler;
		try {
			File logFolder = new File("log");
			logFolder.mkdir();
			rootHandler = new FileHandler(logFolder.toString() + "/"
					+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".log");
			root.addHandler(rootHandler);
			rootHandler.setFormatter(formatter);
		} catch (SecurityException | IOException e) {
			System.err.println("Error on creating log file");
			e.printStackTrace();
		}
	}
}
