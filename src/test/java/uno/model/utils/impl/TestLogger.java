package uno.model.utils.impl;

import uno.model.utils.api.GameLogger;

/**
 * A logger implementation for testing purposes that does not write to files.
 * This prevents the accumulation of log files during unit test execution.
 */
public class TestLogger implements GameLogger {

    @Override
    public void logAction(String playerName, String actionType, String cardDetails, String extraInfo) {
        // No-op for tests.
        // Uncomment the line below if you want to see logs in the console during tests.
        // System.out.println(String.format("[%s] %s: %s (%s) - %s", playerName,
        // actionType, cardDetails, extraInfo));
    }

    @Override
    public void logError(String context, Exception e) {
        // No-op or print to stderr
        // System.err.println("ERROR in " + context + ": " + e.getMessage());
    }
}
