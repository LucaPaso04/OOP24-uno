package uno.view.api;

import java.util.List;

import uno.model.cards.attributes.CardColor;
import uno.model.game.api.GameState;

import java.util.Optional;

/**
 * Data Transfer Object containing the snapshot of the game state
 * required for the View to render itself.
 */
public interface GameViewData {
    /**
     * Indicates whether the turn order is currently clockwise.
     * 
     * @return True if turn order is clockwise, false if counterclockwise.
     */
    boolean isClockwise();

    /**
     * Gets the current state of the game.
     * 
     * @return The current GameState.
     */
    GameState getGameState();

    /**
     * Gets the list of all players in the game.
     * 
     * @return The list of PlayerViewData objects.
     */
    List<PlayerViewData> getPlayers();

    /**
     * Gets the player whose turn it currently is.
     * 
     * @return The current PlayerViewData object.
     */
    PlayerViewData getCurrentPlayer();

    /**
     * Gets the card on top of the discard pile, if any.
     * 
     * @return The top discard card, or Optional.empty() if the pile is empty.
     */
    Optional<CardViewData> getTopDiscardCard();

    /**
     * Indicates whether the discard pile is currently empty.
     * 
     * @return True if the discard pile is empty, false otherwise.
     */
    boolean isDiscardPileEmpty();

    /**
     * Gets the number of cards currently in the draw deck.
     * 
     * @return The number of cards in the draw deck.
     */
    int getDeckSize();

    /**
     * Gets the active color, which may be set by a Wild card.
     * 
     * @return The active color, or Optional.empty() if no color is active.
     */
    Optional<CardColor> getCurrentColor();

    /**
     * Indicates whether the game is being played on the Dark Side.
     * 
     * @return True if the game is being played on the Dark Side, false otherwise.
     */
    boolean isDarkSide();

    /**
     * Indicates whether the current player has already drawn a card this turn.
     * 
     * @return True if the current player has already drawn a card this turn, false otherwise.
     */
    boolean hasCurrentPlayerDrawn();

    /**
     * Gets the winner of the game if the game or round is over.
     * 
     * @return The winner PlayerViewData object, or null if no winner exists.
     */
    PlayerViewData getWinner();
}
