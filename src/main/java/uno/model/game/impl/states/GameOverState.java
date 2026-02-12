package uno.model.game.impl.states;

import uno.model.game.api.GameContext;
import uno.model.game.api.GameState;
import uno.model.game.impl.AbstractGameState;

/**
 * State representing the end of the game.
 */
public class GameOverState extends AbstractGameState {

    /**
     * Constructor for GameOverState.
     * 
     * @param game the game context to which this state belongs
     */
    public GameOverState(final GameContext game) {
        super(game);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getEnum() {
        return GameState.GAME_OVER;
    }
}
