package uno.model.players.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HumanPlayerTest {

    private static final String PLAYER_NAME = "TestPlayer";

    @Test
    void testConstructor() {
        final HumanPlayer p = new HumanPlayer(PLAYER_NAME);
        assertEquals(PLAYER_NAME, p.getName());
    }

    @Test
    void testTakeTurn() {
        final HumanPlayer p = new HumanPlayer(PLAYER_NAME);
        // Verify it doesn't throw and does nothing interactive (passive)
        p.takeTurn(null);
    }
}
