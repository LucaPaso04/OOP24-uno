package uno.model.game.impl.states;

import uno.model.cards.attributes.CardColor;
import uno.model.cards.attributes.CardValue;
import uno.model.cards.types.api.Card;
import uno.model.game.api.GameContext;
import uno.model.game.api.GameState;
import uno.model.game.impl.AbstractGameState;
import uno.model.players.api.AbstractPlayer;
import uno.model.game.impl.ScoreManagerImpl;
import uno.model.game.api.ScoreManager;

import java.util.Optional;

/**
 * State representing the main game loop where players can play cards or draw.
 */
public class RunningState extends AbstractGameState {

    private static final int FINAL_SCORE = 500;

    /**
     * Constructor for RunningState.
     * 
     * @param game The game context to which this state belongs.
     */
    public RunningState(final GameContext game) {
        super(game);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getEnum() {
        return GameState.RUNNING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playCard(final Optional<Card> card) {
        // Validation logic logic moved here from GameImpl

        // 1. Check if it's a valid action in this state (Implicitly yes, since we are
        // in RunningState)

        final AbstractPlayer player = this.getGame().getCurrentPlayer();

        // New Rule: Skip After Draw
        if (this.getGame().getRules().isSkipAfterDrawEnabled() && this.getGame().hasCurrentPlayerDrawn(player)) {
            throw new IllegalStateException("Regola: Skip After Draw. Hai pescato, quindi devi passare il turno.");
        }

        // 2. Controllo se il giocatore ha la carta
        if (!player.getHand().contains(card)) {
            throw new IllegalStateException("Il giocatore non ha questa carta!");
        }

        // 3. Controllo se la mossa è valida secondo le regole
        // Note: isValidMove is private in GameImpl. We will need to make it accessible
        // or move logic here.
        // For now, assume GameImpl will expose it or we call a package-private method.
        if (!this.getGame().isValidMove(card.get())) {
            throw new IllegalStateException("Mossa non valida! La carta " + card + " non può essere giocata.");
        }

        this.getGame().setCurrentPlayedCard(card.get());
        this.getGame().getLogger().logAction(player.getName(), "PLAY",
                card.getClass().getSimpleName(),
                card.get().getValue(this.getGame()).toString());

        // --- FINE LOGICA DI VALIDAZIONE ---

        // Se la mossa è valida, aggiorna il currentColor.
        if (card.get().getColor(this.getGame()) == CardColor.WILD) {
            this.getGame().setCurrentColorOptional(Optional.empty()); // Sarà impostato da onColorChosen()
        } else {
            // Se è una carta colorata, quello è il nuovo colore attivo.
            this.getGame().setCurrentColorOptional(Optional.of(card.get().getColor(this.getGame())));
        }

        // Esegui effetto carta (polimorfismo)
        if (card.get().getValue(this.getGame()) == CardValue.WILD_FORCED_SWAP) {
            // Sposta la carta
            player.playCard(card);
            this.getGame().getDiscardPile().addCard(card.get());

            card.get().performEffect(this.getGame());
        } else {
            card.get().performEffect(this.getGame());

            // Sposta la carta
            player.playCard(card);
            this.getGame().getDiscardPile().addCard(card.get());
        }

        // --- CONTROLLO VITTORIA ---
        if (player.hasWon()) {
            final ScoreManager scoreManager = new ScoreManagerImpl();
            final int points = scoreManager.calculateRoundPoints(player, this.getGame().getPlayers(), this.getGame());
            player.addScore(points);

            String winType = "ROUND_WINNER";
            final boolean scoringMode = this.getGame().getRules().isScoringModeEnabled();

            if (!scoringMode || player.getScore() >= FINAL_SCORE) {
                winType = "MATCH_WINNER";
                this.getGame().setGameState(new GameOverState(this.getGame()));
            } else {
                this.getGame().setGameState(new RoundOverState(this.getGame()));
            }

            this.getGame().setWinner(player);
            this.getGame().getLogger().logAction("SYSTEM", "GAME_OVER", "N/A",
                    "Winner: " + player.getName() + " (" + winType + ") Points: " + points + " Total Score: "
                            + player.getScore());
            this.getGame().notifyObservers();
            return;
        }

        // --- LOGICA DI AVANZAMENTO TURNO ---
        // Se lo stato è cambiato (es. waiting for color), non avanzare qui.
        if (this.getGame().getGameState() == GameState.RUNNING) {
            this.getGame().getTurnManager().advanceTurn(this.getGame());
        }

        this.getGame().notifyObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playerInitiatesDraw() {
        final AbstractPlayer player = this.getGame().getCurrentPlayer();

        // 1. Regola: "Massimo una carta"
        if (this.getGame().hasCurrentPlayerDrawn(player)) {
            throw new IllegalStateException("Hai già pescato in questo turno. Devi giocare la carta o passare.");
        }

        // 2. Regola: "Non se hai carte da giocare"
        if (this.getGame().playerHasPlayableCard(player)) {
            throw new IllegalStateException("Mossa non valida! Hai una carta giocabile, non puoi pescare.");
        }

        // Ok, il giocatore deve pescare
        this.getGame().getTurnManager().setHasDrawnThisTurn(true);

        this.getGame().drawCardForPlayer(player);

        this.getGame().notifyObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playerPassTurn() {
        // Puoi passare solo se hai pescato (perché non avevi mosse)
        // Oppure se hai pescato e la regola "Skip After Draw" è attiva.
        if (!this.getGame().hasCurrentPlayerDrawn(this.getGame().getCurrentPlayer())) {
            // Potresti avere una mossa, quindi non puoi passare
            if (this.getGame().playerHasPlayableCard(this.getGame().getCurrentPlayer())) {
                throw new IllegalStateException("Non puoi passare, hai una mossa valida.");
            } else {
                throw new IllegalStateException("Non puoi passare, devi prima pescare una carta.");
            }
        }

        final AbstractPlayer currentPlayer = this.getGame().getCurrentPlayer();
        final String handSize = String.valueOf(currentPlayer.getHand().size());

        this.getGame().getLogger().logAction(currentPlayer.getName(), "PASS_TURN", "N/A", "HandSize: " + handSize);

        this.getGame().getTurnManager().advanceTurn(this.getGame());
        this.getGame().notifyObservers();
    }
}
