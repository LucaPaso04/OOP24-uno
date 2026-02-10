package uno.model.utils.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameLoggerImplTest {

    private static final String LOG_DIR = "logs";
    private static final String LOG_FILE_PREFIX = "log_match_";
    private static final String LOG_FILE_SUFFIX = ".txt";
    private static final int NEW_FILE = 5;

    @BeforeEach
    void setUp() throws IOException {
        // Clean up logs directory before each test
        deleteLogDirectory();
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up logs directory after each test
        deleteLogDirectory();
    }

    @Test
    void testLogRotation() throws IOException, InterruptedException {
        // Create 6 dummy log files with different timestamps
        final File logDir = new File(System.getProperty("user.dir") + File.separator + LOG_DIR);
        if (!logDir.exists()) {
            final boolean created = logDir.mkdirs();
            if (!created && !logDir.exists()) {
                throw new IOException("Failed to create directory: " + logDir.getAbsolutePath());
            }
        }

        for (int i = 0; i <= NEW_FILE; i++) {
            final File logFile = new File(logDir, LOG_FILE_PREFIX + "test_" + i + LOG_FILE_SUFFIX);
            if (logFile.createNewFile()) {
                final long newTimestamp = System.currentTimeMillis() - (1000L * (10 - i));
                final boolean success = logFile.setLastModified(newTimestamp);

                if (!success) {
                    throw new IOException("Failed to set last modified for: " + logFile.getName());
                }
            }
        }

        // Initialize GameLoggerImpl
        final GameLoggerImpl logger = new GameLoggerImpl("current");

        // Trigger log creation
        logger.logAction("TestPlayer", "TEST", "None", "Testing rotation");

        // Logic verification:
        // We had 6 files.
        // MAX is 5.
        // cleanOldLogs sees 6 files.
        // It deletes (6 - 5 + 1) = 2 files.
        // Remaining: 4 files.
        // Then we created "log_match_current.txt".
        // Total files should be 5.

        final File[] remainingFiles = logDir
                .listFiles((dir, name) -> name.startsWith(LOG_FILE_PREFIX) && name.endsWith(LOG_FILE_SUFFIX));

        // We expect 4 old files + 1 new file = 5 files total.
        assertEquals(NEW_FILE, remainingFiles.length, "Should keep only 5 log files");
    }

    private void deleteLogDirectory() throws IOException {
        final File logDir = new File(System.getProperty("user.dir") + File.separator + LOG_DIR);
        if (logDir.exists()) {
            try (var stream = Files.walk(logDir.toPath())) {
                stream.sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
        }
    }
}
