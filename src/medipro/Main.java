package medipro;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import medipro.config.InGameConfig;
import medipro.gui.frame.GameFrame;

/**
 * メインクラス ログの設定とゲームウィンドウの生成を行う.
 */
public class Main {
    protected Main() {
        // インスタンス化を禁止
        throw new UnsupportedOperationException();
    }

    /**
     * ロガー.
     */
    protected static final Logger LOGGER = Logger.getLogger("medipro");

    /**
     * メインメソッド.
     * 
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        setupLogger();
        System.setProperty("sun.java2d.uiScale", "1");
        LOGGER.info("Start game");
        JFrame window = new GameFrame("GameWindow", InGameConfig.WINDOW_WIDTH, InGameConfig.WINDOW_HEIGHT);
        window.setVisible(true);
    }

    /**
     * ログの設定. 出力先としてコンソールとファイルを設定する.
     */
    private static void setupLogger() {
        LogManager.getLogManager().reset();
        Logger root = Logger.getLogger("medipro");
        Formatter formatter = new LogFormatter();
        // Formatter formatter = new SimpleFormatter();
        // デフォルトのハンドラを削除
        root.setUseParentHandlers(false);
        for (Handler h : root.getHandlers()) {
            if (h instanceof ConsoleHandler) {
                root.removeHandler(h);
            }
        }
        try {
            // ログを保存するフォルダを作成
            File logFolder = new File("log");
            logFolder.mkdir();

            // ファイルへの出力を設定
            Handler rootFileHandler = new FileHandler(logFolder.toString() + "/"
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".log");
            rootFileHandler.setFormatter(formatter);
            rootFileHandler.setLevel(Level.ALL);
            root.addHandler(rootFileHandler);
        } catch (SecurityException | IOException e) {
            System.err.println("Error on creating log file");
            e.printStackTrace();
        }
        // コンソールへの出力を設定
        Handler rootConsoleHandler = new ConsoleHandler();
        rootConsoleHandler.setFormatter(new LogFormatter());
        rootConsoleHandler.setLevel(Level.FINEST);
        root.addHandler(rootConsoleHandler);
        root.setLevel(Level.ALL);

    }
}

/**
 * ログのフォーマットを行うクラス. こちらより引用 https://blog1.mammb.com/entry/2017/02/24/070608.
 */
class LogFormatter extends Formatter {
    /** 日時のフォーマッター. */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");

    /** ログレベルの対応表. */
    private static final Map<Level, String> LEVEL_MSG_MAP = Collections.unmodifiableMap(new HashMap<Level, String>() {
        {
            put(Level.SEVERE, "SEVERE");
            put(Level.WARNING, "WARN");
            put(Level.INFO, "INFO");
            put(Level.CONFIG, "CONF");
            put(Level.FINE, "FINE");
            put(Level.FINER, "FINE");
            put(Level.FINEST, "FINE");
        }
    });

    /** クラス名の長さ. */
    private AtomicInteger nameColumnWidth = new AtomicInteger(16);

    public static void applyToRoot() {
        applyToRoot(new ConsoleHandler());
    }

    public static void applyToRoot(Handler handler) {
        handler.setFormatter(new LogFormatter());
        Logger root = Logger.getLogger("");
        root.setUseParentHandlers(false);
        for (Handler h : root.getHandlers()) {
            if (h instanceof ConsoleHandler)
                root.removeHandler(h);
        }
        root.addHandler(handler);
    }

    @Override
    public String format(LogRecord record) {

        StringBuilder sb = new StringBuilder(200);

        Instant instant = Instant.ofEpochMilli(record.getMillis());
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        sb.append(FORMATTER.format(ldt));
        sb.append(" ");

        sb.append(LEVEL_MSG_MAP.get(record.getLevel()));
        sb.append(" ");

        String category;
        if (record.getSourceClassName() != null) {
            category = record.getSourceClassName();
            if (record.getSourceMethodName() != null) {
                category += " " + record.getSourceMethodName();
            }
        } else {
            category = record.getLoggerName();
        }
        int width = nameColumnWidth.intValue();
        category = adjustLength(category, width);
        sb.append("[");
        sb.append(category);
        sb.append("] ");

        if (category.length() > width) {
            // grow in length.
            nameColumnWidth.compareAndSet(width, category.length());
        }

        sb.append(formatMessage(record));

        sb.append(System.lineSeparator());
        if (record.getThrown() != null) {
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                record.getThrown().printStackTrace(pw);
                pw.close();
                sb.append(sw.toString());
            } catch (Exception ex) {
                System.err.println("Error on formatting log message");
            }
        }

        return sb.toString();
    }

    static String adjustLength(String packageName, int aimLength) {

        int overflowWidth = packageName.length() - aimLength;

        String[] fragment = packageName.split(Pattern.quote("."));
        for (int i = 0; i < fragment.length - 1; i++) {
            if (fragment[i].length() > 1 && overflowWidth > 0) {

                int cutting = (fragment[i].length() - 1) - overflowWidth;
                cutting = (cutting < 0) ? (fragment[i].length() - 1) : overflowWidth;

                fragment[i] = fragment[i].substring(0, fragment[i].length() - cutting);
                overflowWidth -= cutting;
            }
        }

        String result = String.join(".", fragment);
        while (result.length() < aimLength) {
            result += " ";
        }

        return result;
    }
}
