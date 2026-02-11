package uno.view.impl;

import uno.model.cards.attributes.CardColor;
import uno.model.game.api.GameState;
import uno.view.api.CardViewData;
import uno.view.api.PlayerViewData;
import uno.view.api.GameViewData;

import java.util.List;
import java.util.Optional;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Data Transfer Object containing the snapshot of the game state
 * required for the View to render itself.
 * This decouples the View from the Model.
 */
public class GameViewDataImpl implements GameViewData {

    private final GameState gameState;
    private final List<PlayerViewData> players;
    private final PlayerViewData currentPlayer;
    private final Optional<CardViewData> topDiscardCard;
    private final boolean isDiscardPileEmpty;
    private final int deckSize;
    private final Optional<CardColor> currentColor;
    private final boolean isDarkSide;
    private final boolean hasCurrentPlayerDrawn;
    private final PlayerViewData winner;
    private final boolean isClockwise;

    /**
     * Constructs a new GameViewData snapshot.
     *
     * @param gameState             The current state of the game.
     * @param players               The list of all players.
     * @param currentPlayer         The player whose turn it currently is.
     * @param topDiscardCard        The card on top of the discard pile.
     * @param isDiscardPileEmpty    True if discard pile is empty.
     * @param deckSize              The number of cards in the draw deck.
     * @param currentColor          The active color (including Wild choices).
     * @param isDarkSide            True if playing on Dark Side.
     * @param hasCurrentPlayerDrawn True if the current player has already drawn.
     * @param winner                The winner if the game/round is over.
     * @param isClockwise           True if turn order is clockwise.
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public GameViewDataImpl(final GameState gameState,
            final List<PlayerViewData> players,
            final PlayerViewData currentPlayer,
            final Optional<CardViewData> topDiscardCard,
            final boolean isDiscardPileEmpty,
            final int deckSize,
            final Optional<CardColor> currentColor,
            final boolean isDarkSide,
            final boolean hasCurrentPlayerDrawn,
            final PlayerViewData winner,
            final boolean isClockwise) {
        this.gameState = gameState;
        this.players = players;
        this.currentPlayer = currentPlayer;
        this.topDiscardCard = topDiscardCard;
        this.isDiscardPileEmpty = isDiscardPileEmpty;
        this.deckSize = deckSize;
        this.currentColor = currentColor;
        this.isDarkSide = isDarkSide;
        this.hasCurrentPlayerDrawn = hasCurrentPlayerDrawn;
        this.winner = winner;
        this.isClockwise = isClockwise;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isClockwise() {
        return isClockwise;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {
        return gameState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings("EI_EXPOSE_REP")
    public List<PlayerViewData> getPlayers() {
        return players;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerViewData getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CardViewData> getTopDiscardCard() {
        return topDiscardCard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDiscardPileEmpty() {
        return isDiscardPileEmpty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDeckSize() {
        return deckSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CardColor> getCurrentColor() {
        return currentColor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDarkSide() {
        return isDarkSide;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasCurrentPlayerDrawn() {
        return hasCurrentPlayerDrawn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerViewData getWinner() {
        return winner;
    }
}
