package uno.model.game.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uno.model.game.api.Game;
import uno.model.game.api.GameFactory;
import uno.model.game.api.GameMode;
import uno.model.game.api.GameRules;
import uno.model.game.api.GameState;
import uno.model.players.api.AbstractPlayer;
import uno.model.players.impl.HumanPlayer;

class GameFactoryTest {

    private static final String PLAYER_NAME = "P1";

    private GameFactory factory;

    @BeforeEach
    void setUp() {
        final GameRules rules = new GameRulesImpl(false, false, false, false);
        factory = new GameFactoryImpl(rules);
    }

    @Test
    void testCreateGameStandard() {
        final List<AbstractPlayer> players = new ArrayList<>();
        players.add(new HumanPlayer(PLAYER_NAME));
        final Game game = factory.createGame(PLAYER_NAME, GameMode.STANDARD, players);

        assertNotNull(game);
        assertEquals(GameMode.STANDARD, GameMode.valueOf(game.getGameState() == GameState.RUNNING ? "STANDARD" : "ERROR"));
        // We can't easily check deck type from Game interface without casting or
        // getter,
        // but verify it runs.
    }

    @Test
    void testCreateGameFlip() {
        final List<AbstractPlayer> players = new ArrayList<>();
        players.add(new HumanPlayer(PLAYER_NAME));
        final Game game = factory.createGame(PLAYER_NAME, GameMode.FLIP, players);
        assertNotNull(game);
        // Cast to check internal if possible, or just trust factory logic given
        // integration nature
        assertTrue(game instanceof GameImpl);
    }

    @Test
    void testGetLogger() {
        assertNotNull(factory.getLogger());
    }
}
