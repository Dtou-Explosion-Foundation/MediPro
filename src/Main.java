import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
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
import javax.swing.JPanel;

public class Main {
	public static void main(String[] args) {
		setupLogger();
		GameWindow gw = new GameWindow("GameWindow", 1024, 768);
		// JPanel gp = new GamePanel();
		JPanel gp = new MoveCharaPanel();
		gw.add(gp);

		gw.setVisible(true);
	}

	private static void setupLogger() {
		Logger root = Logger.getLogger("");
		Formatter formatter = new SimpleFormatter();
		Handler rootHandler;
		try {
			File logFolder = System.getProperty("user.dir").endsWith("src") ? new File("../log") : new File("log");
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

class GameWindow extends JFrame {

	public GameWindow(String title, int width, int height) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setResizable(false);
	}
}

class GamePanel extends JPanel {
	Image img = Toolkit.getDefaultToolkit().getImage("img/background_sample.png");

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this);
	}
}