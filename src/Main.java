import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JFrame;

import medipro.gui.frame.GameFrame;

public class Main {
	static final Logger logger = Logger.getLogger("");

	public static void main(String[] args) {
		setupLogger();
		logger.info("Start game");
		JFrame window = new GameFrame("GameWindow", 1024, 768);
		window.setVisible(true);
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
