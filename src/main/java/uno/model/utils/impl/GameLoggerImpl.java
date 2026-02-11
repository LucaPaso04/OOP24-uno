package uno.model.utils.impl;

import uno.model.utils.api.GameLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

/**
 * Concrete implementation of GameLogger that writes events to a text file.
 * The logs are stored in a "logs" directory within the project root.
 */
public class GameLoggerImpl implements GameLogger {

    private static final int MAX_LOG_FILES = 5;
    private static final String UNO = "UNO";

    private final String filePath;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Initializes the logger and ensures the directory structure exists.
     *
     * @param matchId A unique identifier for the current match (used in the
     *                filename).
     */
    public GameLoggerImpl(final String matchId) {
        final String userDir = System.getProperty("user.dir");
        this.filePath = userDir + File.separator + "logs" + File.separator + "log_match_" + matchId + ".txt";
        initializeLogDirectory();
    }

    /**
     * Helper method to create the directory structure using Optional to avoid null
     * checks.
     */
    private void initializeLogDirectory() {
        final File logFile = new File(this.filePath);

        Optional.ofNullable(logFile.getParentFile())
                .filter(parent -> !parent.exists())
                .ifPresent(parent -> {
                    if (!parent.mkdirs()) {
                        Logger.getLogger(UNO)
                                .warning("Impossible to create log directory: " + parent.getPath());
                    }
                });

        cleanOldLogs(logFile.getParentFile());
    }

    /**
     * Deletes old log files if the number of log files exceeds the defined limit.
     * 
     * @param logDir The directory where log files are stored.
     */
    private void cleanOldLogs(final File logDir) {
        if (logDir == null || !logDir.exists() || !logDir.isDirectory()) {
            return;
        }

        final File[] logFiles = logDir.listFiles((dir, name) -> name.startsWith("log_match_") && name.endsWith(".txt"));

        if (logFiles != null && logFiles.length >= MAX_LOG_FILES) {
            Arrays.sort(logFiles, Comparator.comparingLong(File::lastModified));

            for (int i = 0; i < logFiles.length - MAX_LOG_FILES + 1; i++) {
                if (!logFiles[i].delete()) {
                    Logger.getLogger(UNO)
                            .warning("Impossible to delete old log file: " + logFiles[i].getPath());
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logAction(final String playerName, final String actionType, final String cardDetails,
            final String extraInfo) {
        final String timestamp = dtf.format(LocalDateTime.now());

        final String logEntry = String.format("%s;%s;%s;%s;%s",
                timestamp, playerName, actionType, cardDetails, extraInfo);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8, true))) {
            writer.write(logEntry);
            writer.newLine();
        } catch (final IOException e) {
            Logger.getGlobal().log(SEVERE,
                    "Impossible to write to log file: " + filePath, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logError(final String context, final Exception e) {
        this.logAction("SYSTEM_ERROR", context, e.getClass().getSimpleName(), e.getMessage());

        Logger.getLogger(UNO)
                .log(SEVERE, context, e);
    }
}
